package com.gw.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TermSerice {

	private String g_initChar = new String();// 存放原始汉字
	private List<String> g_twolist = new ArrayList<String>();
	private List<String> g_threelist = new ArrayList<String>();
	private List<String> g_fourlist = new ArrayList<String>();
	private List<String> g_list = new ArrayList<String>();
	private int g_twosum = 0;
	private int g_threesum = 0;
	private int g_foursum = 0;

	/**
	 * 加载原始汉字数据
	 * 
	 * @param response
	 * @param request
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	public void LoadChar(HttpServletRequest request, HttpServletResponse response) throws IOException {

		InputStreamReader fis = null;

		try {

			// 声明文件对象，request.getSession().getServletContext().getRealPath()获取发布后项目的绝对路径
			File file = new File(request.getSession().getServletContext().getRealPath("/files/500ChinaCharacter.txt"));

			// 文件对象没有读写能录，设置缓冲区来进行读写=
			fis = new InputStreamReader(new FileInputStream(file), "GBK");
			// 设置一个字节缓存
			char[] data = new char[1024];
			// 得到实际读取的字节数
			int n = 0;

			while ((n = fis.read(data)) != -1) {
				g_initChar = new String(data, 0, n);
			}

			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("文件读取失败");
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 产生随机汉字
	 * 
	 * @return
	 */
	public String Extract(char[] effectChar) {

		int size = effectChar.length;
		int[] randNumber = new int[size];// 存放产生的随机数组
		char[] initChar = g_initChar.toCharArray();

		for (int i = 0; i < size; i++) {
			randNumber[i] = (int) (Math.random() * 500);
			effectChar[i] = initChar[randNumber[i]];
		}
		return new String(effectChar);

	}

	/**
	 * 汉字排序
	 * 
	 * @param request
	 * @param response
	 * @param effectChar
	 */
	public List<String> SortChar(HttpServletRequest request, HttpServletResponse response, String effectChar) {

		Combination(request, response, effectChar.toCharArray());

		g_list.addAll(g_twolist);
		g_list.addAll(g_threelist);
		g_list.addAll(g_fourlist);

		g_list.add(Integer.toString(g_twosum));
		g_list.add(Integer.toString(g_threesum));
		g_list.add(Integer.toString(g_foursum));

		return g_list;

	}

	/**
	 * 实现全组合
	 */
	private void Combination(HttpServletRequest request, HttpServletResponse response, char[] effectChar) {

		int n = effectChar.length;
		int nbit = 1 << n;
		int x = 0;
		int m = 0;
		char[] charBuff = new char[n];

		if (effectChar.length == 1 && effectChar.length == 0) {
			return;
		}

		for (int i = 0; i < nbit; i++) {
			for (int j = 0; j < n; j++) {

				int tmp = 1 << j;

				if ((tmp & i) != 0) {
					charBuff[x++] = effectChar[j];
				}
			}
			if (charBuff[1] != '\0') {

				if (x == n) {
					m = n;
				}
				if (x != n) {
					for (m = 0; charBuff[m] != '\0'; m++)
						;
				}

				String string = new String(charBuff, 0, m);
				Permutation(request, response, string.toCharArray(), 0);
				string = null;
			}

			for (int k = 0; k < x; k++) {
				charBuff[k] = '\0';
			}
			x = 0;
		}

	}

	/**
	 * 递归实现交换
	 * 
	 * @param index
	 */
	private void Permutation(HttpServletRequest request, HttpServletResponse response, char[] charBuff, int index) {

		if (index > charBuff.length) {
			return;
		}

		if (index == charBuff.length) {
			Contrast(request, response, charBuff);
		}

		for (int i = index; i < charBuff.length; i++) {
			Swap(charBuff, index, i);
			Permutation(request, response, charBuff, index + 1);
			Swap(charBuff, index, i);
		}
	}

	/**
	 * 汉字交换核心算法
	 * 
	 * @param index
	 * @param taget
	 */
	private void Swap(char[] charBuff, int index, int taget) {

		char tmp = charBuff[index];
		charBuff[index] = charBuff[taget];
		charBuff[taget] = tmp;
	}

	/**
	 * 进行对比，判断词语是否有效
	 * 
	 * @param charBuff
	 */
	private void Contrast(HttpServletRequest request, HttpServletResponse response, char[] charBuff) {

		File file;
		InputStreamReader fis = null;
		BufferedReader reader = null;
		String line = null;

		int n = charBuff.length;

		if (n >= 5) {
			return;
		}

		try {

			file = new File(
					request.getSession().getServletContext().getRealPath("/files/ContrastCharacter" + n + ".txt"));
			fis = new InputStreamReader(new FileInputStream(file), "GBK");
			reader = new BufferedReader(fis);
			while ((line = reader.readLine()) != null) {
				if (new String(charBuff).equals(line)) {
					if (n == 2) {
						g_twolist.add(new String(charBuff));
						g_twosum++;
						break;
					} else if (n == 3) {
						g_threelist.add(new String(charBuff));
						g_threesum++;
						break;
					} else if (n == 4) {
						g_fourlist.add(new String(charBuff));
						g_foursum++;
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("对比失败！");
		}
	}

}
