
package org.lightfw.utilx.web.request;

import java.util.SortedSet;
import java.util.TreeSet;

public class UrlUtil {
	
	/**
	 * 匹配路径是否在控制域的范围内
	 * 
	 * @param urls
	 * @param path
	 * @return
	 */
	public static boolean urlMatch(SortedSet<String> urls, String path) {
		
		if(urls == null || urls.size() == 0)
			return false;

		SortedSet<String> hurl = urls.headSet(path + "\0");
		SortedSet<String> turl = urls.tailSet(path + "\0");

		if (hurl.size() > 0) {
			String before = hurl.last();
			if (pathMatch(path, before))
				return true;
		}

		if (turl.size() > 0) {
			String after = turl.first();
			if (pathMatch(path, after))
				return true;
		}

		return false;
	}

	/**
	 * 匹配路径是否在控制域的范围内
	 * 
	 * @param path
	 * @param domain
	 * @return
	 */
	private static boolean pathMatch(String path, String domain) {
		if (PathPatternMatcher.isPattern(domain)) {

			return PathPatternMatcher.match(domain, path);

			// TODO 使用正则方式验证，待验证
			// domain = domain.replaceAll("\\*", "\\\\S*");
			//
			// Pattern pattern = Pattern.compile(domain);
			//				
			// Matcher matcher = pattern.matcher(path);
			// return matcher.matches();

		} else {
			return domain.equals(path);
		}
	}
	
	public static void main(String[] args) {
		String path = "/ht-callout/customer.action";
		
		SortedSet<String> urls = new TreeSet<String>();
		urls.add("/ht-callout/*");
		urls.add("/ht-sys/*");
		urls.add("/ht-report/*");
		urls.add("/*");
		urls.add("");
		
		boolean b = urlMatch(urls, path);
		
		System.out.println(b);
	}

}
