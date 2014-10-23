package com.rest.tiis.util.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;
import com.rest.tiis.beans.User;
import com.rest.tiis.filter.SpringObjectFactoryHolder;
import com.rest.tiis.service.UserService;

public class NoticeTest {
	private static final String APPID = "djUGtMrQ8A64fC664vH137";
	private static final String APPKEY = "hKGLMQHjGr88YgzVlEzkb8";
	private static final String MASTERSECRET = "1vCOsPMlFu7dUzZZWVm1c9";
	private static String CLIENTID = "";
	private static final String API = "http://sdk.open.api.igexin.com/apiex.htm"; // OpenService接口地址

	@Autowired
	private static UserService userService = (UserService) SpringObjectFactoryHolder.getContext().getBean("userServiceImpl");

	public static void main(String[] args) {
		// pushMsg("00000001");
//		pushAllMsg("这个是测试通知，你懂的 !");
		pushUpdateMsg();
	}

	public static void pushMsg(String hostCode) {
		// 推送主类
		IIGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);
		try {
			List<User> list = new ArrayList<User>();
			list = userService.selectUserByHostCode(hostCode);
			if (!CollectionUtils.isEmpty(list)) {
				// 接收者
				List<Target> targets = new ArrayList<Target>();
				ListMessage message = new ListMessage();

				// 通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate，此处以TransmissionTemplate为例
				NotificationTemplate template = new NotificationTemplate();
				template.setAppId(APPID);
				template.setAppkey(APPKEY);
				template.setTitle("通知【Rainet云灌溉】"); // 通知标题
				template.setText("您有一个节点监测的湿度过低!");
				template.setLogo("push.png");

				// template.setIsRing(true); // 收到通知是否响铃，可选，默认响铃
				// template.setIsVibrate(true); // 收到通知是否震动，可选，默认振动
				// template.setIsClearable(true); // 通知是否可清除，可选，默认可清除

				// 收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动
				template.setTransmissionType(2);

				// template.setTransmissionContent("你需要透传的内容"); //
				// 透传内容（点击通知后SDK将透传内容传给你的客户端，需要客户端做相应开发）

				message.setData(template);
				message.setOffline(true); // 用户当前不在线时，是否离线存储,可选
				message.setOfflineExpireTime(72 * 3600 * 1000); // 离线有效时间，单位为毫秒，可选
				for (User user : list) {
					CLIENTID = user.getClientid();

					Target target = new Target();
					target.setAppId(APPID);
					target.setClientId(CLIENTID);
					targets.add(target);
				}
				String contentId = push.getContentId(message);
				IPushResult ret = push.pushMessageToList(contentId, targets);

				System.out.println(ret.getResponse().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void pushAllMsg(String Notice) {
		// 推送主类
		IIGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);

		try {

			AppMessage message = new AppMessage();
			// 通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate，此处以LinkTemplate为例
			NotificationTemplate template = new NotificationTemplate();

			template.setAppId(APPID); // 应用APPID
			template.setAppkey(APPKEY); // 应用APPKEY

			// 通知属性设置：如通知的标题，内容
			template.setTitle("警告【Rainet云灌溉】"); // 通知标题
			template.setText(Notice); // 通知内容
			template.setLogo("push.png");

			message.setData(template);
			message.setOffline(true); // 用户当前不在线时，是否离线存储，可选，默认不存储
			message.setOfflineExpireTime(72 * 3600 * 1000);

			List<String> appIdList = new ArrayList<String>();
			appIdList.add(APPID);

			message.setAppIdList(appIdList);

			push.pushMessageToApp(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void pushUpdateMsg() {
		// 推送主类
		IIGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);

		try {

			// 单推消息类型
			SingleMessage message = new SingleMessage();

			// 通知栏弹框下载模版
			NotyPopLoadTemplate template = new NotyPopLoadTemplate();
			// 是否激活
			template.setActived(true);
			// 安卓标识
			template.setAndroidMark("android_mark");
			template.setAppId(APPID);
			template.setAppkey(APPKEY);
			// 是否自动安装
			template.setAutoInstall(true);
			// 是否响铃
			template.setBelled(true);
			// 通知是否可清除
			template.setCleared(true);
			// 苹果标识
			template.setIphoneMark("iphone_mark");
			// 下载图标
			template.setLoadIcon("push.png");
			// 下载标题
			template.setLoadTitle("Rainet云灌溉");
			// 下载地址
			template.setLoadUrl("http://dizhensubao.igexin.com/dl/com.ceic.apk");
			// 通知栏内容
			template.setNotyContent("有新版本可以升级更新!");
			// 通知栏图标
			template.setNotyIcon("push.png");
			// 通知栏标题
			template.setNotyTitle("版本升级【Rainet云灌溉】");
			// 左侧按钮名称
			template.setPopButton1("取消");
			// 右侧按钮名称
			template.setPopButton2("下载");
			// 弹框内容
			template.setPopContent("有新版本可以下载更新!");
			// 弹框图标
			template.setPopImage("push.png");
			// 弹框标题
			template.setPopTitle("下载");
			// 塞班标识
			template.setSymbianMark("symbian_mark");
			// 是否震动
			template.setVibrationed(true);
			message.setData(template);
			message.setOffline(true);
			message.setOfflineExpireTime(72 * 3600 * 1000);
			// 设置优先级
			message.setPriority(1);

			Target target1 = new Target();
			target1.setAppId(APPID);
			target1.setClientId("e094bf9331218d0ddcef653c80235a59");
			// 单推
			IPushResult ret = push.pushMessageToSingle(message, target1);
			System.out.println(ret.getResponse().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
