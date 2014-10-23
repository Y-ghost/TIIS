package com.rest.tiis.util.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import org.apache.log4j.Logger;

import com.rest.tiis.beans.DataTemp;
import com.rest.tiis.filter.SpringObjectFactoryHolder;
import com.rest.tiis.service.NetWorkService;
import com.rest.tiis.util.CodingFactoryUtil;

/**
 * @project: TIIS
 * @Title: ServerHandler.java
 * @Package com.rest.tiis.util.network
 * @Description: 服务端handler
 * @author 杨贵松
 * @date 2014年2月17日 下午8:50:33
 * @version V1.0
 */
public class ServerHandler extends ChannelInitializer<SocketChannel> {

	private final Logger log = Logger.getLogger(ServerHandler.class.getName());
	private CodingFactoryUtil codingFactory = new CodingFactoryUtil();
	private NetWorkService netWorkService = (NetWorkService) SpringObjectFactoryHolder.getContext().getBean("netWorkServiceImpl");

	@Override
	public void initChannel(SocketChannel ch){
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("decoder", new LengthFieldBasedFrameDecoder(256, 5, 1, 0, 0));
		pipeline.addLast("handler", new ChannelInboundHandlerAdapter() {
			@Override
			public void channelActive(ChannelHandlerContext ctx){
				log.info("新连接成功~");
			}

			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg){
				ByteBuf buf = (ByteBuf) msg;
				byte[] message = new byte[buf.readableBytes()];
				buf.getBytes(0,message);
				String data = codingFactory.bytesToHexString(message);
				
				//监控主机地址
				String address ="";
				String contralId ="";
				try {
					if(data.length()==22){
						//心跳包数据，将信道Channel保存到缓存
						address = data.substring(12,20);
						ChannelSession.put(address, ctx.channel());
						log.info("服务器接收"+address+"监控主机的注册包或者心跳包 : " + data);
						buf.clear();
					}else if(data.substring(22,24).equals("3B")){
						address = data.substring(14,22);
						contralId = data.substring(22,24);
						
						long date = System.currentTimeMillis();
						DataTemp dt =  netWorkService.selectData(address,contralId);
						if(dt == null || (date -dt.getReceivetime().getTime()) > 6*3600*1000){
							//将监控主机返回的数据存储，以备解析调用
							log.info("服务器接收"+address+"监控主机的报警数据 : " + data);
							netWorkService.saveNetData(address,contralId,data);
							//只有超过6个小时间隔的才报警
							netWorkService.pushMsg(address);//接到报警，推送给用户
						}else{
							log.info("服务器接收"+address+"监控主机的报警数据为满足推送条件!");
						}
						
						buf.clear();
					}else if(data.substring(data.length()-2,data.length()).equals("36")){//若末尾字节为36，表示客户端要通过服务器向监控主机发送指令
						address = data.substring(14,22);
						contralId = data.substring(22,24);
						byte[] newMsg = codingFactory.string2BCD(data.substring(0,data.length()-2)+"35");//将36更换成35发送
						//根据地址找到改地址的监控主机信道Channel,并发送指令
						Channel channel = ChannelSession.get(address);
						if(channel==null){
							log.info("服务器未找到"+address+"监控主机的信道，该信道可能未注册或者已关闭!");
						}else{
							buf.clear();
							buf.writeBytes(newMsg);
							channel.writeAndFlush(buf);
							log.info("服务器向"+address+"监控主机转发的数据 : " + data.substring(0,data.length()-2)+"35");
						}
					}else  if(data.substring(data.length()-2,data.length()).equals("35")){
						address = data.substring(14,22);
						contralId = data.substring(22,24);
						log.info("服务器接收"+address+"监控主机的数据 : " + data);
						//将监控主机返回的数据存储，以备解析调用
						netWorkService.saveNetData(address,contralId,data);
						buf.clear();
					}else{
						log.info("服务器接收到的脏数据 : " + data);
						buf.clear();
					}
				} catch (Exception e) {
					buf.clear();
					ctx.close();
					log.error("服务器接收数据异常！"+e);
				}
			}
		});
	}
}
