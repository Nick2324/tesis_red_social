package com.sna_deportivo.utils.gr;

public class StringUtils {

	public static final String QUOTATIONMARK = "&#34;";
	public static final String COLON = "&#58;";
	public static final String OPENINGSQUARE = "&#91;";
	public static final String CLOSINGSQUARE = "&#93;";
	public static final String OPENINGCURLY = "&#123;";
	public static final String CLOSINGCURLY = "&#125;";
	public static final String NEWLINE = "&#13; &#10;";
	
	public static String codificar(String aCodificar){
		if(aCodificar != null)
			return aCodificar.replace("\"", StringUtils.QUOTATIONMARK)
				   .replace(":", StringUtils.COLON)
				   .replace("[", StringUtils.OPENINGSQUARE)
				   .replace("]", StringUtils.CLOSINGSQUARE)
				   .replace("{", StringUtils.OPENINGCURLY)
				   .replace("}", StringUtils.CLOSINGCURLY)
				   .replace("\n", StringUtils.NEWLINE)
				   .replace("\\","");
		else
			return aCodificar;
	}
	
	public static String decodificar(String aDecodificar){
		if(aDecodificar != null)
			return aDecodificar.replace(StringUtils.QUOTATIONMARK, "\"")
				   .replace(StringUtils.COLON, ":")
				   .replace(StringUtils.OPENINGSQUARE, "[")
				   .replace(StringUtils.CLOSINGSQUARE, "]")
				   .replace(StringUtils.OPENINGCURLY, "{")
				   .replace(StringUtils.CLOSINGCURLY, "}")
				   .replace(StringUtils.NEWLINE,"\n");
		else
			return aDecodificar;
	}
	
	public static boolean compararStrings(String string1, String string2){
		return (string1 == null && string2 == null) || 
			   (string1 != null && string1.equals(string2));
	}
	
	public static String corregirDobleBackslash(String aCorregir){
		if(aCorregir != null){
			aCorregir = aCorregir.replace("\\","");
		}
		
		return aCorregir;
		
	}
	
}
