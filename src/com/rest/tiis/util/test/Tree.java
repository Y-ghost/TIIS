package com.rest.tiis.util.test;

public class Tree {
	private static int a = 0;
	private static int b = 10000000;

	int data;
	Tree left;
	Tree right;

	public Tree(int data) {
		this.data = data;
		left = null;
		right = null;
	}

	public void insert(Tree root, int data) { // 向二叉树中插入子节点
		if (data > root.data) {
			if (root.right == null) {
				root.right = new Tree(data);
			} else {
				this.insert(root.right, data);
			}
		} else {
			if (root.left == null) {
				root.left = new Tree(data);
			} else {
				this.insert(root.left, data);
			}
		}
	}

	public static void preOrder(Tree root) { // 先根遍历
		if (root != null) {
			System.out.println(root.data);
			if(root.data>a){
				a = root.data;
			}
			if(root.data < b){
				b = root.data;
			}
			preOrder(root.left);
			preOrder(root.right);
		}
	}


	public static void main(String[] str) {
		int[] arr = { 58, 60, 31, 34, 45, 46, 12, 23, 90, 11 };
		Tree root = new Tree(arr[0]); // 创建二叉树
		for (int i = 1; i < arr.length; i++) {
			root.insert(root, arr[i]);// 向二叉树中插入数据
		}
		
		preOrder(root);
		System.out.println(a+"-------"+b);
	}
}