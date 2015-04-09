object MorseFunc	
{
	/**
	 * MorseReader class used to interpret Morse code or translate to Morse code.
	 * 
	 * METHODS:
	 	* translate
	 		* USE:
	 			* PARAMETERS:
	 				* s:String	{Phrase to be translated to or from Morse.}
	 			* If Translate to Morse mode is enabled, inserted string will be translated to Morse.
	 			* Else code will attempt to be translated from Morse to English.
	 		* RETURNS:
	 			* A string of either English or Morse characters.
	 		* EXCEPTIONS:
	 			* If given string contains unsupported characters an IllegalArgumentException is thrown.
	 	
	 	* switchTranslationMode
	 		* USE:
	 			* Switching to and from Translate To Morse mode. 
	 			* Will switch from true to false or false to true depending on current setting.

		* getTranslationMode
			* USE:
				* Returns what the current translation mode is.
			* RETURNS:
				* True if Translating to Morse
				* False otherwise.
	 */
	class MorseReader()
	{
		private val MorseToEnglish = MorseLanguageDetails.MorseToEnglish;
		private val EnglishToMorse = MorseLanguageDetails.EnglishToMorse;
		private var TransToMorse   = true;		
	
		private def transPhraseToMorse(phrase:String):String =
		{
			var inPhrase	:String				= phrase;
			var inWords 	:List[String]		= phrase.split(' ').toList;
			var outWords	:String				= "";
			
			var invalidChars:Array[String]		= Array();
			var shouldThrow :Boolean			= false;
      
      
			for (i <- 0 to inWords.length - 1)
			{
				var charStrings:Array[String] = GeneralFunc.CharArrayToStringArray(inWords(i).toCharArray());
				
				
				//Loop scans each letter in the word.
				for(j <- 0 to charStrings.length - 1)	
				{
					if (this.EnglishToMorse.contains(charStrings(j).toString().toUpperCase()))	
					{
						charStrings(j) = this.EnglishToMorse.apply(charStrings(j).toUpperCase());
					}
					else	
					{
						invalidChars = invalidChars :+ charStrings(j);
						shouldThrow  = true;
					}
					
					//Check if a letter follows, in which case there should be a space appended to prepare for it.
					if (j != charStrings.length - 1 && charStrings(j) != "\n")	
					{
						outWords += (charStrings(j) + " ");
					}
					else	
					{
						outWords += charStrings(j);
					}
				}
				
				// Check to make sure we are not at the last word and adds a / to prepare for the next word if we aren't.
				// / denotes a space in between two words in the Morse language (when written).
				if (i != inWords.length - 1)	
				{
					outWords += " / ";
				}
				
			}
			
			if (!shouldThrow)
			{
				return outWords;
			}
      
			throw new IllegalArgumentException("ERROR: Unknown symbols[" + GeneralFunc.ArrayStringToString(invalidChars, true) + "]");
      
		}
		
		private def transPhraseToEnglish(phrase:String):String =
		{
			var inPhrase 	                = phrase;
			var inLines 					= phrase.split("\n").map(_.trim);
      
			var invalidChars:Array[String]  = Array();
			var shouldThrow :Boolean        = false;
      
			//Go through each letter and replace it with the correct Morse equivalent.
			for (i <- 0 to inLines.length - 1)	{
				var WordsInLine = inLines(i).split(' ');
				for (j <- 0 to WordsInLine.length - 1)
				{
					if (WordsInLine.equals("\n")) println("\\n Found");
					if (this.MorseToEnglish.contains(WordsInLine(j)))	
					{
						WordsInLine(j) = this.MorseToEnglish.apply(WordsInLine(j));
					}
					else
					{
						invalidChars = invalidChars :+ WordsInLine(j);
						shouldThrow  = true;
					}
				}
				inLines(i) = GeneralFunc.ArrayStringToString(WordsInLine);
			}
		  
			if (!shouldThrow)
			{
				return GeneralFunc.ArrayStringToStringInterpolateNewLines(inLines);
			}
      
			throw new IllegalArgumentException("ERROR: Unknown symbols[" + GeneralFunc.ArrayStringToString(invalidChars, true) + "]");
      
		}
		
		def translate(s: String):String =
		{		
			if (this.TransToMorse)
			{
				return this.transPhraseToMorse(s);
			}
			
			return this.transPhraseToEnglish(s);

		}
		
		def switchTranslationMode() =
		{
			this.TransToMorse = !this.TransToMorse;
		}
		
		def getTranslationMode():Boolean =
		{
			return this.TransToMorse;
		}
	}
	
}