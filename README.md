# navgen
A simple groovy class to generate the shit ton of files needed from some screens with navigation in React Native.
The generated code is shaped in stone to fit some pattern I learned in a course. Feel free to fork it and adapt to your own cases.

Run the jar passing the class name as parameter, like this:
`java -jar navgen.jar chat`

Existing files will not be touched unless force parameter is used. Just pass `--force` or `-f`

Maybe you, imaginary reader, could ask: why not NPM or something like that? Two things:
- I didn't want to;
- I missed playing around with Groovy after a couple of years away.
