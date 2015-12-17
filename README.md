# FCNotifier
Send a message once your file has finished downloading, installing, or deleting 

#####NOTE: Current version is only Mac compatible 

Checks the file or directory to see if any writes have been detected within an interval; if no writes have been detected after the file has initially begun modification, then the file or directory is done.

######Things to modify:

Within the `SendEmail.java` class, modifications need to be made for `String username` and `String password` which need to be a valid gmail account. I would recommend setting up a email bot using gmail.

######Sending a message:
Firstly you must enter the email address of the recipient and then add a custom message to what you wish to send them. 

**Note:** Many cell phone providers allow the access of sending SMS messages via email. An extensive list may be found [here]



[//]: #
[here]: <http://martinfitzpatrick.name/list-of-email-to-sms-gateways>
