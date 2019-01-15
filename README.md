# cs1631
project for the class

Milestone 1: Test interface between remote control and component. (5 points) You can download and install the SIS server, Java components (and Android components) following the system installation procedure. You should have already started the implementation of the component, concentrating on the interface with the server. Please study the following temperature monitoring component to develop your own component. You will demonstrate how your component can interact with the remote control. The scenario is as follows: First you run the remote control, which will appear on the screen. Then you use the remote control to send a message to your component. The message typically consists of some variables and their values. You can enter the variable names and values through the remote control. The output message should also appear on the remote control. If you are using Java, test your component using the remote control (Java version). The instructions on how to use the remote control is described in the ReadMe_V5.txt. For smart phones there is a remote control (Android version) in the Android software package downloadable following the System Installation Procedure described above. The remote control (Android version) has multiple menu pages. Use your finger to flip the page to the right, to find menu page you need. Remote control (Android version) requires the 'READ_EXTERNAL_STORAGE' permission to read a file from a local storage card. If the permission is already defined in the 'AndroidManifest.xml' file, then your xml files can be stored on the external storage card of your smart phone. If you want to run the remote control under Windows there is also an older version of remote control (prjRemote.exe) that you can download.


Milestone 2: Give a demo of your component. (15 points for CS1631) The demo is similar to the demo you give at the first milestone, except that the details of the application of your component should be demonstrated. For those working on Android app for smart phones, you may combine the Input Processor and Voting Software components into one Android program. You can then follow the phone to phone scenario to demonstrate your project. For those working on phone to PC system, if you have not successfully implemented the Input Processor to transform incoming phone call into CastVote message 701, you can still use the remote control to give the demo following the phone to PC scenario. You should pre-store the messages (as XML documents) so that you can do the demo quickly. You can also instantiate more than one remote control in your demo, in case you need them. The grading is 10 points for successful demo, 2 points for voter usability, 2 points for administrator usability and 1 point for security. 

Note: The Android function for your smart phone to transform sms message into text message is probably described in: http://stackoverflow.com/questions/11435354/receiving-sms-on-android-app 
If it is not in the above website, use Google to look for the appropriate function. Beware that different versions of Android may behave differently on different smart phones from different vendors. Sometimes permission from user may be needed. The following is the Android source code for requesting permission from user. You may need it if your Android sdk version is greater than 21.

        if (android.os.Build.VERSION.SDK_INT >=
android.os.Build.VERSION_CODES.M) {

            if
(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to
ACCESS_FINE_LOCATION - requesting it");
                String[] permissions = {Manifest.permission.BLUETOOTH};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
             }
        }



Rehearsal: (bonus points) If your group have successfully implemented the InputProcessor and VotingSoftware Components (or a combined component) for the Android app, you will be rehearsing to deal with the real voting events on CSDay. 



Milestone 3: Based upon agile programming, give final demo of an integrated project involving two groups and at least one extra component (the GUI or the Trends Analyzer or both) and provide final report. You can also choose to use Ionic Framework to do cross-platform software development and port your app from one platform to another (20 points). 

The extra componenent can be the GUI replacing the more primitive PrjRemote. The GUI can be used to test all types of administrative messages: 700, 701, 702 and new types of messages for Trends Analyzer, and to run test scripts based upon test-driven development (TDD). Minimally the GUI should be able to load a test script consisting of a sequence of messages (in XML format) and send the messages to the component(s) being tested.
The extra component can also be the Trends Analyzer that analyzes the voting trends of people with different interests, in different age group/education background/income group, etc. using a simple knowledge-base.

If two groups are working together on MS3, one group can take on the implementation and management of the extra component(s) and the other group can design the test scripts and do testing and measurements. 

The final documentation is in the form of a zip file. When unzipped, it contains the following:

A Word file, which is your primary documentation. It contains the updated and enhanced documents that you submitted in Exercise 4 and previous milestones. The document thus consists of: a) enhanced project plan, b) updated UML diagrams, c) revised messages and variables, and d) a detailed example or test script with explanation and screen shots showing how your component works. (The last item is especially important in case Murphy's Law strikes again and your component does not work during the demo. At least you have something to show on paper.) The appendix of this document is the source codes, XML messages, PDF and/or WORD files.
The source codes. Yes, the source codes should be included both as an appendix in the report and also separately in a subdirectory Source.
updated PDF or WORD file for the updated UML diagrams.
A subdirectory Message containing the XML messages. 
Please name your zip file, WORD file, PDF file using the first five letters of your last name. For example, your name is Davenport. Then your zip file is daven.zip, your WORD file is daven.doc, your PDF file is daven.pdf. For team projects use one of the team member's name. You will lose 2 points if you do not follow this naming convention. 
