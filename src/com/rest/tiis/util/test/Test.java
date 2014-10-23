package com.rest.tiis.util.test;

import java.text.DecimalFormat;

import com.rest.tiis.util.CodingFactoryUtil;



public class Test {
	public static void main(String[] args) {
		CodingFactoryUtil codingFactory = new CodingFactoryUtil();
//		byte[] eCode = codingFactory.string2BCD("0001");
//		byte[] eData = codingFactory.longToByte(Long.parseLong("20000"));
//		byte[] a = codingFactory.byteMerger(eCode, eData);
		
//		StringBuffer sb = new StringBuffer ("123456789");
//		System.out.println(sb.reverse());
		byte[] b = {0x01,0x00,0x01};
		byte[] c = codingFactory.coding((byte)0x01, "00000002", (byte)0x22, b);
		System.out.println(codingFactory.bytesToHexString(c));
	}
	
	/**
	 * @Title: 				byteMerger 
	 * @author 				杨贵松
	 * @date 				2014年7月3日 下午12:19:23
	 * @Description: 		java 合并两个byte数组  
	 * @param byte_1
	 * @param byte_2
	 * @return 
	 * byte[] 				返回
	 */
    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2){  
        byte[] byte_3 = new byte[byte_1.length+byte_2.length];  
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);  
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);  
        return byte_3;  
    }  
    
	/** 
	 * @Title: 				longToByte 
	 * @author 				杨贵松
	 * @date 				2014年7月3日 下午12:06:14
	 * @Description: 		倒序转换
	 * @param number
	 * @return 
	 * byte[] 				返回 
	 */
	public static byte[] longToByte(long number) { 
		long temp = number; 
		byte[] b = new byte[4]; 
		for (int i = 0; i < b.length; i++) { 
			b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位 
			temp = temp >> 8; // 向右移8位 
		} 
		return b; 
	} 
	
	/**
	 * @Title: 				toByteArray 
	 * @author 				杨贵松
	 * @date 				2014年7月3日 下午12:19:32
	 * @Description: 		正序转换
	 * @param number
	 * @return 
	 * byte[] 				返回
	 */
	public static byte[] toByteArray(long number) {
		long temp = number;
		byte[] b = new byte[4];
		for (int i = b.length - 1; i > -1; i--) {
			b[i] = new Long(temp & 0xff).byteValue();
			temp = temp >> 8;
		}
		return b;
	}

	public static long toLong(byte[] b) {
		long l = 0;
		l = b[0];
		l |= ((long) b[1] << 8);
		l |= ((long) b[2] << 16);
		l |= ((long) b[3] << 24);
		l |= ((long) b[4] << 32);
		l |= ((long) b[5] << 40);
		l |= ((long) b[6] << 48);
		l |= ((long) b[7] << 56);
		return l;
	}
}
