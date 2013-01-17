ProducerConsumer
================

Simple producer consumer implementation with an example.


<b>RejectionStrategy</b> 
The strategy to use when a Task has been rejected by the Queue. An example FileRejectionStrategy can be found in the test folder.

<b>ResumeStrategy</b> 
The strategy to use to resume tasks previously rejected by the Queue. An exmaple FileResumeStrategy can be found in the test folder.


<b>How to use:</b><br>
Queue can be created using 2 constructors.

<code>Queue queue = new Queue(new RejectionStrategy());</code>
or
<code>Queue queue = new Queue(new RejectionStrategy(), new ResumeStrategy());</code>

If a ResumeStrategy has been specified, the queue needs to the told to start looking for failed tasks. That can be done like so:
<code>queue.start();</code>

The example in the test folder shows how the queue can been used. It produces emails with file attachments. The task simply prints the email contents (doesn't actually send it). It utilizes full rejection and resumption capabilities of the queue.


This is a maven project built for Java 5 and above. Non maven users will need to download Apache's CommonsIO and add to classpath for running the test. No other dependencies needed for the project.