package com.rest.tiis.util.network;

import java.util.List;

import com.rest.tiis.util.CodingFactoryUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class BytesDecoder extends LengthFieldBasedFrameDecoder{
	private CodingFactoryUtil codingFactory = new CodingFactoryUtil();
	public BytesDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
	}

	@Override
	protected byte[] decode(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
		if(buf==null){
			return null;
		}
		byte[] bytes = new byte[buf.readInt()];
		buf.readBytes(bytes);
		System.out.println(codingFactory.bytesToHexString(bytes));
		return bytes;
	}
	
	@Override
	protected void decodeLast(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		super.decodeLast(ctx, in, out);
	}
}
