package com.ben.monlabel.web.publique;

import java.io.InputStream;
import java.util.Arrays;


public class MPeg3 {
	
	enum Version{
		VERSION1,
		VERSION2;
	}
	
	enum Layer{
		LAYER3;
	}

	enum Channel{
		STEREO,
		JOINT_STEREO,
		DUAL_MONO_CHANNELS,
		MONO;
	}
	
	String[] genres = {"Blues", "Classic rock", "Country", "Dance", "Disco", "Funk", "Grunge", "Hip-Hop", "Jazz",
			"Metal", "New Age", "Oldies", "Autre", "Pop", "R'n'B", "Rap", "Reggae", "Rock", "Techno", "Industrial",
			"Rock alternatif", "Ska", "Death metal", "Pranks", "Musique de film", "Euro-Techno", "Ambient",
			"Trip-hop", "Vocal", "Jazz-Funk", "Fusion", "Trance", "Classical", "Instrumental", "Acid", "House",
			"Musique de jeu vidéo", "Extrait sonore (Sound Clip ou Sample)", "Gospel", "Musique bruitiste (Noise)",
			"Rock alternatif", "Bass", "Soul", "Punk", "Space", "Musique de médiation (Meditative)", "Pop instrumental",
			"Rock instrumental", "Musique ethnique", "Gothique", "Darkwave", "Techno-Industrial", "Musique électronique",
			"Pop-Folk", "Eurodance", "Dream", "Rock sudiste (Southern Rock)", "Comédie", "Cult", "Gangsta", "Hit-parade (Top 40)",
			"Christian Rap", "Pop/Funk", "Jungle", "Musique amérindienne", "Cabaret", "New wave", "Psychédélique",
			"Rave", "Comédie musicale (Showtunes)", "Bande-annonce", "Lo-fi", "Musique tribale", "Acid Punk", "Acid Jazz",
			"Polka", "Rétro", "Théâtre", "Rock & Roll", "Hard Rock", "Folk", "Folk rock", "Folk américain", "Swing",
			"Fast Fusion", "Bebop", "Musique latine", "Revival", "Musique celtique", "Bluegrass", "Avantgarde",
			"Gothic Rock", "Rock progressif", "Rock psychédélique", "Rock symphonique", "Slow Rock", "Big Band",
			"Chœur", "Easy listening", "Acoustique", "Humour", "Discours", "Chanson", "Opéra", "Musique de chambre",
			"Sonate", "Symphonie", "Booty Bass", "Primus[Quoi ?]", "Porn groove", "Satire", "R'n'B contemporain ou Slow Jam",
			"Club", "Tango", "Samba", "Folklore", "Ballade", "Power ballad", "Rythmic soul", "Freestyle", "Duo", "Punk rock",
			"Drum Solo", "A cappella", "Euro-house", "Dancehall", "Goa", "Drum and bass", "Club House", "Hardcore", "Terror", 
			"Indie", "BritPop", "Negerpunk", "Polsk Punk", "Beat", "Gangsta rap chrétien", "Heavy metal", "Black metal",
			"Crossover", "Musique chrétienne contemporaine", "Rock chrétien", "Merengue", "Thrash metal", "Animé", "JPop", "Synthpop"};
	
	Version version;
	Layer layer;
	Channel channel;
	int bitRate;
	int sampleRate;
	String titre;
	String interprete;
	String nomAlbum;
	String anneeParution;
	String commentaire;
	String genre;
	
	public MPeg3(InputStream inputStream) throws Exception{
		
		int octet;
	    boolean frameSync = false;
	    
	    int nbOctet = 0;
	    
	    do{
	    	octet = inputStream.read();
	    	if(octet == -1){
	    		throw new Exception("Fin du fichier atteinte avant MP3 Tag.");
	    	}
	    	nbOctet++;
	    	
	    	octet = octet & 0xFF;
		    // si octet = 255 cherche le suivant pour savoir si frame sync (11 bits à 1)
		    if((octet & 0xFF) == 0xFF){
		    	
		    	octet = inputStream.read();
		    	if(octet == -1){
		    		throw new Exception("Fin du fichier atteinte avant MP3.");
		    	}
		    	nbOctet++;
		    	
		    	octet = octet & 0xFF;
			    // checher si 3 premiers bits à 1 (les 3 bits qui font 11 avec l'octet precedent
			    if((octet & 0xE0) == 0xE0){
			    	frameSync = true;
			    }
		    }
	    }while(frameSync == false);
	    
	    if((octet & 0x18) == 0x18){
	    	version = Version.VERSION1;
	    } else if((octet & 0x18) == 0x10){
	    	version = Version.VERSION2;
	    } else {
	    	throw new Exception("Erreur de Mpeg version (ni 1, ni 2).");
	    }
	    
	    if((octet & 0x06) == 0x02){
	    	layer = Layer.LAYER3;
	    } else {
	    	throw new Exception("Erreur de Layer Mpeg (pas 3).");
	    }
	    
	    octet = inputStream.read();
    	if(octet == -1){
    		throw new Exception("Fin du fichier atteinte avant MP3.");
    	}
    	nbOctet++;
    	
    	octet = octet & 0xF0;
    	
    	switch(version){
    	case VERSION1:
    		if(octet == 0x10){
    			bitRate = 32;
    		} else if(octet == 0x20){
    			bitRate = 40;
    		} else if(octet == 0x30){
    			bitRate = 48;
    		} else if(octet == 0x40){
    			bitRate = 56;
    		} else if(octet == 0x50){
    			bitRate = 64;
    		} else if(octet == 0x60){
    			bitRate = 80;
    		} else if(octet == 0x70){
    			bitRate = 96;
    		} else if(octet == 0x80){
    			bitRate = 112;
    		} else if(octet == 0x90){
    			bitRate = 128;
    		} else if(octet == 0xA0){
    			bitRate = 160;
    		} else if(octet == 0xB0){
    			bitRate = 192;
    		} else if(octet == 0xC0){
    			bitRate = 224;
    		} else if(octet == 0xD0){
    			bitRate = 256;
    		} else if(octet == 0xE0){
    			bitRate = 320;
    		} else {
    			throw new Exception("Erreur bit rate MPeg3.");
    		}
    		break;
    	case VERSION2:
    		if(octet == 1){
    			bitRate = 8;
    		} else if(octet == 0x20){
    			bitRate = 16;
    		} else if(octet == 0x30){
    			bitRate = 24;
    		} else if(octet == 0x40){
    			bitRate = 32;
    		} else if(octet == 0x50){
    			bitRate = 40;
    		} else if(octet == 0x60){
    			bitRate = 48;
    		} else if(octet == 0x70){
    			bitRate = 56;
    		} else if(octet == 0x80){
    			bitRate = 64;
    		} else if(octet == 0x90){
    			bitRate = 80;
    		} else if(octet == 0xA0){
    			bitRate = 96;
    		} else if(octet == 0xB0){
    			bitRate = 112;
    		} else if(octet == 0xC0){
    			bitRate = 128;
    		} else if(octet == 0xD0){
    			bitRate = 144;
    		} else if(octet == 0xE0){
    			bitRate = 160;
    		} else {
    			throw new Exception("Erreur bit rate MPeg3.");
    		}
    		break;
    	}
    	
    	octet = inputStream.read();
    	if(octet == -1){
    		throw new Exception("Fin du fichier atteinte avant MP3.");
    	}
    	nbOctet++;
    	
    	octet = octet & 0xFF;
    	
    	switch(version){
    	case VERSION1:
    		if((octet & 0xF0) == 0x00){
        		sampleRate = 44100; 
        	} else if((octet & 0xF0) == 0x40){
        		sampleRate = 48000; 
        	} else if((octet & 0xF0) == 0x80){
        		sampleRate = 32000; 
        	} else {
        		throw new Exception("Erreur sample rate MPeg3.");
        	} 
    		break;    		
    	case VERSION2:
    		if((octet & 0xF0) == 0x00){
        		sampleRate = 22050; 
        	} else if((octet & 0xF0) == 0x40){
        		sampleRate = 24000; 
        	} else if((octet & 0xF0) == 0x80){
        		sampleRate = 16000; 
        	} else {
        		throw new Exception("Erreur sample rate MPeg3.");
        	} 
    		break;
    	}
    	
    	octet = inputStream.read();
    	if(octet == -1){
    		throw new Exception("Fin du fichier atteinte avant MP3.");
    	}
    	nbOctet++;
    	
    	octet = octet & 0xFF;
    	
    	if((octet & 0xC0) == 0x00){
    		channel = Channel.STEREO; 
    	} else if((octet & 0xC0) == 0x40){
    		channel = Channel.JOINT_STEREO; 
    	} else if((octet & 0xC0) == 0x80){
    		channel = Channel.DUAL_MONO_CHANNELS; 
    	} else if((octet & 0xC0) == 0x80){
    		channel = Channel.MONO;
    	} 
    	
	    byte[] id3 = new byte[128];
	    long size = inputStream.available();
	    size = size - 128;
	    inputStream.skip(size);
	    if(inputStream.read(id3) != -1){
	    	String TAG = new String(id3, 0, 3);
	    	if(TAG.equals("TAG") == false){
	    		throw new Exception("Erreur TAG ID3 MPeg3.");
	    	}
	    	titre = new String(id3, 3, 30);
	    	titre = titre.trim();
	    	interprete = new String(id3, 33, 30);
	    	interprete = interprete.trim();
	    	nomAlbum = new String(id3, 63, 30);
	    	nomAlbum = nomAlbum.trim();
	    	anneeParution = new String(id3, 93, 4);
	    	anneeParution = anneeParution.trim();
	    	commentaire = new String(id3, 97, 30);
	    	commentaire = commentaire.trim();
	    	int index = id3[127];
	    	if(index>=0 && index<=147){
	    		genre = genres[index];
	    		genre = genre.trim();
	    	}
	    } else {
	    	throw new Exception("Erreur TAG ID3 MPeg3.");
	    }

	}

	public Version getVersion() {
		return version;
	}

	public Layer getLayer() {
		return layer;
	}

	public Channel getChannel() {
		return channel;
	}

	public int getBitRate() {
		return bitRate;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public String[] getGenres() {
		return genres;
	}

	public String getTitre() {
		return titre;
	}

	public String getInterprete() {
		return interprete;
	}

	public String getNomAlbum() {
		return nomAlbum;
	}

	public String getAnneeParution() {
		return anneeParution;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public String getGenre() {
		return genre;
	}

	@Override
	public String toString() {
		return "MPeg3 [version="
				+ version + ", layer=" + layer + ", channel=" + channel
				+ ", bitRate=" + bitRate + ", sampleRate=" + sampleRate
				+ ", titre=" + titre + ", interprete=" + interprete
				+ ", nomAlbum=" + nomAlbum + ", anneeParution=" + anneeParution
				+ ", commentaire=" + commentaire + ", genre=" + genre + "]";
	}

	
}
