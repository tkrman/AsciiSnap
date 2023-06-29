# AsciiSnap
Program that converts .txt files to a .png file. Mainly used for ASCII art.


Alright so I made this because I was making a playlist cover for my spotify account and I wanted to use ASCII art so it would look cool,
and because I'm a friggin' nerd I was like, "Oh wait! When I was browsing the web I found that site with all the cool ASCII art and they
had a program that converts ASCII to a .png file. I'll just use that." Unfortunately it required me to use the command prompt in order to
choose the file and convert it and it REALLY just ticked me off. We live in the FUTURE god damn it, we are not cavemen having to chain
ourselves to crappy command prompt lines and the barren wasteland that is the terminal. So my demented lil noggin' got to scheming, what
if when you run the program it just opens a window where you click a button and you simply find your file in the file explorer dialog; then
save it as a .png using another file explorer dialog so you can easily choose where to save it? This is really a simple concept honestly,
this is why we have UI/UX developers cause we need those sacred GUIs to guide us, the people, for it is the shepard that guides us lost
souls to salvation. That is, a simple, efficent user experience without the dreaded 20 lines of terminal-slop. So voila!

HOW TO USE:
> You'll notice three buttons and a checkbox. The first button "Choose File" simple opens a file explorer dialog and let's you select any
  .txt file of your choosing. Default option is white colored background with black, monospaced font of 20 point size. Clicking the checkbox
  will make the background transparent. What can I say? I'm a nice guy.
> The second button "Convert and Save" simply converts the .txt into a .png file in the background. It then pulls up a file dialog to let
  you easily save your new .png file wherever you want.
> The last button "Quit" simply closes the program. Really not that necessary but it was originally a "Clear" button, but I figured it was
  unnessesary because if you need to swap out your .txt file you can just click "Choose File", select the new file and it will replace
  the old one.

NOTICE:
Due to the algorithm being a pain in the ASS, I had to MacGyver some fixes. Not sure how it'll affect anything else, BUT there is one thing
I know for sure it affects. The FIRST and LAST line of the .txt file will NOT BE PRINTED!!! Before, the program would misalign the first and 
last lines, and thus making the ASCII art look like ASCII fart. After fighting with it for like two hours and getting nowhere I just said 
SCREW IT! Basically, what I'm trying to say is take that into account before you go and try to convert your .txt file. I'm not entirely sure
using a blank line as a buffer would actually work, it seemed to misalign on the first/last lines that actually had stuff to print. My 
suggestion would be to put random crap on the lines that'll get cut out so that way it only cuts out the useless crap. If that doesn't
work, well honestly I think you found your calling and you should TOTALLY program something better and send me it (tee hee :D )    


