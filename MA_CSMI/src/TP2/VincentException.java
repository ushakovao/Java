package TP2;

public class VincentException extends RuntimeException{  
	
	/** 
	* Crée une nouvelle instance de VincentException 
	*/  
	public VincentException() {}  
	/** 
	* Crée une nouvelle instance de VincentException 
	* @param message Le message détaillant exception 
	*/  
	public VincentException(String message) {  
		super(message); 
	}  
	/** 
	* Crée une nouvelle instance de VincentException 
	* @param cause L'exception à l'origine de cette exception 
	*/  
	public VincentException(Throwable cause) {  
		super(cause); 
	}  
	/** 
	* Crée une nouvelle instance de VincentException 
	* @param message Le message détaillant exception 
	* @param cause L'exception à l'origine de cette exception 
	*/  
	public VincentException(String message, Throwable cause) {  
		super(message, cause); 
	} 
}