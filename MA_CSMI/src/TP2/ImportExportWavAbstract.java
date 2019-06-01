package TP2;

public class ImportExportWavAbstract {


	protected String fileTypeBlocID;
	protected long fileSizeMinus8;
	protected String format;
	protected String formatBlocID;
	protected long blockSize;
	protected int audioFormat;
	protected int nbChannels; // must be written in WavJava
	protected long frequency; // must be written in WavJava
	protected long myByteRate;
	protected int bytePerBlock;
	protected int bitsPerSample; // divided by 8, it is written in WavJava as bytesPerSample
	protected String dataChunkID;
	protected long dataSize;

	protected byte [] data; // must be transform into double, and written into wavJava


	protected String filePath;
	protected WavJava wavJava;

	// The pressure is scaled into the interval of doubles [0,maxScale]
	protected static double maxOf2bytesUnsignedInteger = Math.pow(2,16)-1;  
	
	
	
	/*
    WAV File Specification
    FROM http://ccrma.stanford.edu/courses/422/projects/WaveFormat/
   The canonical WAVE format starts with the RIFF header:
   0         4   ChunkID          Contains the letters "RIFF" in ASCII form
                                  (0x52494646 big-endian form).
   4         4   ChunkSize        36 + SubChunk2Size, or more precisely:
                                  4 + (8 + SubChunk1Size) + (8 + SubChunk2Size)
                                  This is the size of the rest of the chunk 
                                  following this number.  This is the size of the 
                                  entire file in bytes minus 8 bytes for the
                                  two fields not included in this count:
                                  ChunkID and ChunkSize.
   8         4   Format           Contains the letters "WAVE"
                                  (0x57415645 big-endian form).

   The "WAVE" format consists of two subchunks: "fmt " and "data":
   The "fmt " subchunk describes the sound data's format:
   12        4   Subchunk1ID      Contains the letters "fmt "
                                  (0x666d7420 big-endian form).
   16        4   Subchunk1Size    16 for PCM.  This is the size of the
                                  rest of the Subchunk which follows this number.
   20        2   AudioFormat      PCM = 1 (i.e. Linear quantization)
                                  Values other than 1 indicate some 
                                  form of compression.
   22        2   NbChannels      Mono = 1, Stereo = 2, etc.
   24        4   SampleRate=frequency       8000, 44100, etc.
   28        4   ByteRate         == SampleRate * NumChannels * BitsPerSample/8
   32        2   BlockAlign       == NumChannels * BitsPerSample/8
                                  The number of bytes for one sample including
                                  all channels. I wonder what happens when
                                  this number isn't an integer?
   34        2   BitsPerSample    8 bits = 8, 16 bits = 16, etc.

   The "data" subchunk contains the size of the data and the actual sound:
   36        4   Subchunk2ID      Contains the letters "data"
                                  (0x64617461 big-endian form).
   40        4   Subchunk2Size    == NumSamples * NumChannels * BitsPerSample/8
                                  This is the number of bytes in the data.
                                  You can also think of this as the size
                                  of the read of the subchunk following this 
                                  number.
   44        *   Data             The actual sound data.


NOTE TO READERS:

The thing that makes reading wav files tricky is that java has no unsigned types.  This means that the
binary data can't just be read and cast appropriately.  Also, we have to use larger types
than are normally necessary.

In many languages including java, an integer is represented by 4 bytes.  The issue here is
that in most languages, integers can be signed or unsigned, and in wav files the  integers
are unsigned.  So, to make sure that we can store the proper values, we have to use longs
to hold integers, and integers to hold shorts.

Then, we have to convert back when we want to save our wav data.

It's complicated, but ultimately, it just results in a few extra functions at the bottom of
this file.  Once you understand the issue, there is no reason to pay any more attention
to it.


ALSO:

This code won't read ALL wav files.  This does not use to full specification.  It just uses
a trimmed down version that most wav files adhere to.
	 */



	/**
	 * we print the whole informations,
	 * but only the 1000 first bytes of data
	 */
	@Override
	public String toString() {

		StringBuffer dataBuffer = new StringBuffer(", data=[");
		for (int i=0;i<1000 && i< data.length;i++ )
			dataBuffer.append(" "+data[i]);
		dataBuffer.append("]");

		return "Wav [fileTypeBlocID=" + fileTypeBlocID
				+ ", fileSizeMinus8=" + fileSizeMinus8 + ", format=" + format
				+ ", formatBlocID=" + formatBlocID + ", blockSize="
				+ blockSize + ", audioFormat=" + audioFormat + ", nbChannels="
				+ nbChannels + ", frequency=" + frequency + ", myByteRate="
				+ myByteRate + ", bytePerBlock=" + bytePerBlock
				+ ", bitsPerSample=" + bitsPerSample + ", dataChunkID="
				+ dataChunkID + ", dataSize=" + dataSize +
				dataBuffer+
				"] \n";
	}


	// ===========================
	// CONVERT BYTES ARRAY TO DOUBLES AND CONVERSELY
	// ===========================

//	
//	public double byteArrayOfSize2ToDouble(byte[] array){
//		double res=0;
//		
//		if (array.length!=2) throw new VincentException("le tableau d'octet n'a pas la bonne taille");
//		else {
//			int intRes = byteArrayToInt(array);
//			if (intRes<0 || intRes>maxOf2bytesUnsignedInteger) throw new VincentException("l'entier converti est négatif ou trop grand");
//			else res = intRes/maxOf2bytesUnsignedInteger;
//		}			
//		return res;		
//	}


//	
//	public byte[] doubleToByteArrayOfSize2(double d){
//		
//		byte [] preRes= new byte[4];
//		byte [] res= new byte[2];
//		
//		
//		if (d<0 || d>1) throw new VincentException("le double n'est pas entre 0 et 1");
//		else {
//			//first we convert the double into an integer (positive)
//			int intRes = (int) (d * maxOf2bytesUnsignedInteger);
//			preRes = intToByteArray(intRes);
//			res[0]=preRes[0];
//			res[1]=preRes[1];
//		}
//		
//		return res;
//	}


	// ===========================
	// CONVERT BYTES TO JAVA TYPES
	// ===========================



	//  convert a byte of size 2 into an a unsigned-integer of two byte. Because we want it unsigned, we return an int
	public int byteArrayToInt(byte[] b)
	{
		int low = b[0] & 0xff;
		int high = b[1] & 0xff;
		return (int) ( high << 8 | low );
	}




	//  convert a byte of size 4 into an a unsigned-integer of 4 bytes. Because we want it unsigned, we return an long
	public long byteArrayToLong(byte[] b)
	{
		int i = 0;
		int cnt = 0;
		byte[] tmp = new byte[4];
		for (i = 0; i <  4 ; i++){
			tmp[cnt] = b[i];
			cnt++;
		}
		long accum = 0;
		i = 0;
		for ( int shiftBy = 0; shiftBy < 32; shiftBy += 8 ){
			accum |= ( (long)( tmp[i] & 0xff ) ) << shiftBy;
			i++;
		}
		return accum;
	}


	// ===========================
	// CONVERT JAVA TYPES TO BYTES
	// ===========================
	// returns a byte array of length 4
	public byte[] intToByteArray(int i){
		byte[] b = new byte[4];
		b[0] = (byte) (0xFF & (i >> 8 * 0));
		b[1] = (byte) (0xFF & (i >> 8 * 1));
		b[2] = (byte) (0xFF & (i >> 8 * 2));
		b[3] = (byte) (0xFF & (i >> 8 * 3));
		return b;
	}


	// convert a short to a byte array
	// be carefull, the small digits are on position 0, while big digits in position 1
	public byte[] shortToByteArray(short s){
		return new byte[]{ (byte) ( s & 0xff) , (byte)((s >> 8) & 0xff)};
	}


}