
package TP2;


import java.io.DataInputStream;
import java.io.FileInputStream;


import java.io.DataInputStream;
import java.io.FileInputStream;


public class ImportWav extends ImportExportWavAbstract{


	public ImportWav(String aFilePath,WavJava aWavJava){
		super();

		filePath=aFilePath;
		wavJava=aWavJava;


		DataInputStream inFile = null;
		byte[] tmp4bytes = new byte[4];
		byte[] tmp2bytes = new byte[2];

		// each time we open a file, there may ne a mistake of path
		try{
			inFile = new DataInputStream(new FileInputStream(filePath));

			/*
			 * [Bloc de déclaration d'un fichier au format WAVE]
			 *
			/*
			 * FileTypeBlocID  (4 octets) : Constante «RIFF»  (0x52,0x49,0x46,0x46)
			 */
			fileTypeBlocID = "" + (char)inFile.readByte() + (char)inFile.readByte() + (char)inFile.readByte() + (char)inFile.readByte();
			/*
			 * FileSize        (4 octets) : Taille du fichier moins 8 octets (les 4*2 octets précédents)
			 */
			inFile.read(tmp4bytes); // read the ChunkSize
			fileSizeMinus8 = byteArrayToLong(tmp4bytes);
			/*
			 * FileFormatID    (4 octets) : Format = «WAVE»  (0x57,0x41,0x56,0x45)
			 */
			format = "" + (char)inFile.readByte() + (char)inFile.readByte() + (char)inFile.readByte() + (char)inFile.readByte();

			/*
			 * [Bloc décrivant le format audio]
			 */

			/*
			 * formatBlocID    (4 octets) : Identifiant «fmt »  (0x66,0x6D, 0x74,0x20)
			 */
			formatBlocID = "" + (char)inFile.readByte() + (char)inFile.readByte() + (char)inFile.readByte() + (char)inFile.readByte();

			/*
			 * BlocSize        (4 octets) : Nombre d'octets du bloc - 8  (0x10)
			 */
			inFile.read(tmp4bytes);
			blockSize = byteArrayToLong(tmp4bytes);

			/*
			 * AudioFormat     (2 octets) : Format du stockage dans le fichier (1: PCM, ...)
			 */
			inFile.read(tmp2bytes); // read the audio format.  This should be 1 for PCM
			audioFormat = byteArrayToInt(tmp2bytes);

			/*
			 * NbChanel       (2 octets) : Nombre de canaux (de 1 à 6)
			 *       1 pour mono,
      		2 pour stéréo
      		3 pour gauche, droit et centre
      		4 pour face gauche, face droit, arrière gauche, arrière droit
      		5 pour gauche, centre, droit, surround (ambiant)
      		6 pour centre gauche, gauche, centre, centre droit, droit, surround (ambiant)
			 */
			inFile.read(tmp2bytes);
			nbChannels = byteArrayToInt(tmp2bytes);

			/*
			 * Frequence    (4 octets) : Fréquence d'échantillonnage (en hertz) [Valeurs standardisées : 11025, 22050, 44100 et éventuellement 48000 et 96000]
			 */
			inFile.read(tmp4bytes);
			frequency = byteArrayToLong(tmp4bytes);

			/*
			 * BytePerSec      (4 octets) : Nombre d'octets à lire par seconde (i.e., Frequence * BytePerBloc).
			 */
			inFile.read(tmp4bytes);
			myByteRate = byteArrayToLong(tmp4bytes);

			/*
			 * BytePerBloc     (2 octets) : Nombre d'octets par bloc d'échantillonnage (i.e., tous canaux confondus : NbrCanaux * BitsPerSample/8).
			 */
			inFile.read(tmp2bytes);
			bytePerBlock = byteArrayToInt(tmp2bytes);

			/*
			 * BitsPerSample   (2 octets) : Nombre de bits utilisés pour le codage de chaque échantillon (8, 16, 24)
			 */
			inFile.read(tmp2bytes);
			bitsPerSample = byteArrayToInt(tmp2bytes);


			/*
			 * [Bloc des données]
			 */

			/*
			 * DataBlocID      (4 octets) : Constante «data»  (0x64,0x61,0x74,0x61)
			 */
			dataChunkID = "" + (char)inFile.readByte() + (char)inFile.readByte() + (char)inFile.readByte() + (char)inFile.readByte();
			/*
			 * dataSize        (4 octets) : Nombre d'octets des données (i.e. "Data[]", i.e. taille_du_fichier - taille_de_l'entête  (qui fait 44 octets normalement).
			 */
			inFile.read(tmp4bytes); // read the size of the data
			dataSize = byteArrayToLong(tmp4bytes);


			/*
			 * Filling of wavJava
			 */
			wavJava.bytesPerSample = bitsPerSample / 8;
			wavJava.frequency=frequency;
			wavJava.nbChannels=nbChannels;


			/*
			 * DATAS[] : [Octets du Sample 1 du Canal 1] [Octets du Sample 1 du Canal 2] [Octets du Sample 2 du Canal 1] [Octets du Sample 2 du Canal 2]
			 */



			data = new byte[(int) dataSize];
			inFile.read(data);
			inFile.close();
			// close the input stream



			// convertions of data into samples

			int nbSample = (int) (dataSize / wavJava.bytesPerSample/wavJava.nbChannels);
			if(wavJava.nbChannels>1){
				wavJava.samplesList = new double[wavJava.nbChannels][nbSample];
			}
			else{
				wavJava.samples = new double[nbSample];
			}


			//now we can compute the duration
			wavJava.lastTimeDrawn=wavJava.getDuration();


			/*
			 * Recall : the bytesPerSample can be 1,2,4
			 * but we only implement 2.
			 * Be carefull, usually in the wav file,
			 * for bytesPerSample =1  the samples are unsigned  !!!!
			 * for bytesPerSample =2  the samples are signed
			 * for bytesPerSample =4  the samples are signed
			 */


			if (wavJava.bytesPerSample==2){
				if(nbChannels>1) {
					for (int chan = 0; chan < nbChannels; chan++) {
						for (int i = 0; i < nbSample; i++) {

							// we recompose the short. Take care of the order
							int low = data[2*nbChannels * i +2*chan] & 0xff;
							int high = data[2*nbChannels * i +2*chan+ 1] & 0xff;
							short a = (short) (high << 8 | low);

							// to get a double between -1 and +1 we have to devide by Short.MAX_VALUE+1
							// because Short.MIN_VALUE =  - Short.MAX_VALUE- 1
							wavJava.samplesList[chan][i] = (double) a / (Short.MAX_VALUE + 1);

						}
					}
				}
				else{
					for (int i = 0; i < nbSample; i++) {

						// we recompose the short. Take care of the order
						int low = data[2 * i] & 0xff;
						int high = data[2 * i + 1] & 0xff;
						short a = (short) (high << 8 | low);

						// to get a double between -1 and +1 we have to devide by Short.MAX_VALUE+1
						// because Short.MIN_VALUE =  - Short.MAX_VALUE- 1
						wavJava.samples[i] = (double) a / (Short.MAX_VALUE + 1);

					}
				}
			}
			else if (wavJava.bytesPerSample==4){

				if(nbChannels>1) {
					for (int chan = 0; chan < nbChannels; chan++) {
						for (int i = 0; i < nbSample; i++) {

							// we recompose the short. Take care of the order
							int b1 = data[4*nbChannels * i +2*chan] & 0xff;
							int b2 = data[4*nbChannels * i +2*chan + 1] & 0xff;
							int b3 = data[4*nbChannels * i +2*chan + 2] & 0xff;
							int b4 = data[4*nbChannels * i +2*chan + 3] & 0xff;
							int a = (int) (b4 << 8 * 3 | b3 << 8 * 2 | b2 << 8 | b1);

							// to get a double between -1 and +1 we have to devide by Short.MAX_VALUE+1
							// because Short.MIN_VALUE =  - Short.MAX_VALUE- 1
							wavJava.samplesList[chan][i] = (double) a / (Integer.MAX_VALUE + 1);

						}
					}
				}
				else {

					for (int i = 0; i < nbSample; i++) {

						// we recompose the short. Take care of the order
						int b1 = data[4 * i] & 0xff;
						int b2 = data[4 * i + 1] & 0xff;
						int b3 = data[4 * i + 2] & 0xff;
						int b4 = data[4 * i + 3] & 0xff;
						int a = (int) (b4 << 8 * 3 | b3 << 8 * 2 | b2 << 8 | b1);

						// to get a double between -1 and +1 we have to devide by Short.MAX_VALUE+1
						// because Short.MIN_VALUE =  - Short.MAX_VALUE- 1
						wavJava.samples[i] = (double) a / (Integer.MAX_VALUE + 1);

					}
				}
			}
			else throw new VincentException("pour l'instant, bytesPerSample ne peut valoir que 2, et ici " +wavJava.bytesPerSample);


		}
		catch(Exception e){
			System.err.println("ATTENTION: " + e.getMessage());
			data=null;
			wavJava=null;
		}




	}




}