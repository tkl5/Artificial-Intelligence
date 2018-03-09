'''
Created on Dec 1, 2017

@author: Tim Liu
@version: December, 2017
Trigram Markov Model 
'''
import random

theWordLimit = 1000
theTrigramList = []

class trigram():
    
    def __init__(self, theDepth, theCurrentGram):
        self.myTextList = []
        self.theWord = theCurrentGram[0];
        self.theDepth = theDepth;
        self.theCount = 1;
        if (len(theCurrentGram) > 1):
            self.myTextList.append(trigram(theDepth + 1, theCurrentGram[1:]))
        
    def updateWord(self, newWord):
        self.theCount += 1
        
        for firstWord in self.myTextList:
            
            if (newWord[1] == firstWord.theWord):
                firstWord.theCount += 1
                
                for nextWord in firstWord.myTextList:
                    if (newWord[2] == nextWord.theWord):
                        nextWord.theCount += 1
                        return
                    
                firstWord.myTextList.append(trigram(2, newWord[2:]))
                return
            
        self.myTextList.append(trigram(1, newWord[1:]))
        return
    
# method to add all texts to the myEntireTextList.
# comment out text besides doyle-27.txt and doyle-case-27.txt for Sherlock Holmes stories only. 
def appendAllTexts(myEntireTextList): 
    myEntireTextList.append('alice-27.txt')
    myEntireTextList.append('doyle-27.txt')
    myEntireTextList.append('doyle-case-27.txt')
    myEntireTextList.append('london-call-27.txt')
    myEntireTextList.append('melville-billy-27.txt')
    myEntireTextList.append('twain-adventures-27.txt')

#class to train all the word models using a supplied list of text files.
def trainer(): 
    myCurrentList = []
    myEntireTextList = []
    wordFound = False
    
    appendAllTexts(myEntireTextList)
    
    #Go through each text file
    for text in myEntireTextList:
        print(text)
        #Open each text file as currentTextFile
        with open(text, "r") as currentTextFile:
            #Go through each line in a text file
            for line in currentTextFile:
                #Go through each word in  current line
                for word in line.split():
                    myCurrentList.append(word.lower())
                    
                    if (len(myCurrentList) == 3):
                        
                        for currentGram in theTrigramList:
                            if (currentGram.theWord == myCurrentList[0]):
                                currentGram.updateWord(myCurrentList)
                                wordFound = True
                                break
                        
                        if (wordFound == False):
                            theTrigramList.append(trigram(0, myCurrentList))
                            
                        myCurrentList.remove(myCurrentList[0])
                        wordFound = False
         
        # Empty the list
        myCurrentList[:] = []    
         
         
def generateAllText():
    generatedText = []
    highestFreqOfOccurence = 0;
    wordFound = False
    theThirdWord = ""
        
    while(len(generatedText) < theWordLimit):
        if (len(generatedText) == 0):
                
            indexOne = random.randint(0, len(theTrigramList) - 1)
            theFirstWord = theTrigramList[indexOne]
                
            generatedText.append(theFirstWord.theWord)
            
        else:
            wordFound = False
                
            for currentGram in theTrigramList:
                    
                if (currentGram.theWord == theThirdWord.theWord):
                    theFirstWord = currentGram
                    wordFound = True
                        
            if (wordFound == False):
                indexOne = random.randint(0, len(theTrigramList) - 1)
                theFirstWord = theTrigramList[indexOne]
                generatedText.append(theFirstWord.theWord)
                
                
        indexTwo = random.randint(0, len(theFirstWord.myTextList) - 1)
        theSecondWord = theFirstWord.myTextList[indexTwo]
        
        del(theThirdWord)
        highestFreqOfOccurence = 0
            
        for word in theSecondWord.myTextList:
            #Pr(W, W+1, W+2) / Pr(W+1,W+2)
                
            currentFreqCount = (word.theCount)/(float(theSecondWord.theCount))
                
            if (currentFreqCount > highestFreqOfOccurence):
                theThirdWord = word
                highestFreqOfOccurence = currentFreqCount
                    
            
        generatedText.append(theSecondWord.theWord)
        generatedText.append(theThirdWord.theWord)
    
    # Comment output file name accordingly        
    generatedTextOut = open('AllTextOutput.txt', 'w')
    #generatedTextOut = open('HolmesTextOutput.txt', 'w')
        
    for word in generatedText:
        generatedTextOut.write(word + ' ')
        
trainer()
generateAllText()
            
            
    
                
                
                
                
                
                
                
                
                
                
                
        
    

