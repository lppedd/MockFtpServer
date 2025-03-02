# MockFtpServer Change Log

Version 3.1.0 (Sep 2022)
------------------------------------------
Enhancements and Bug Fixes
 - #18: Fix UnixFakeFileSystem and WindowsFakeFileSystem thread safety. Use ConcurrentHashMap.
 - #17: DefaultSession: Fix slow readData(). Use BufferedInputStream.
Infrastructure and Dependencies
 - #19: Upgrade main dependencies: slf4j-api to 2.0.0.
 - #19: Upgrade test dependencies: groovy-all to 3.0.12; logback-classic to 1.3.0; mockito-core to 4.7.0; 
        junit-jupiter-engine to 5.9.0; spring-core and spring-context to 5.2.22.RELEASE.


Version 3.0.0 (Sep 2021)
------------------------------------------
Enhancements and Bug Fixes
 - #14: `UnixDirectoryListingFormatter`: When formatting lastModified timestamp, use "MMM dd HH:mm" for recent files and "MMM dd  yyyy" for older files (> 180 days old)
 - #15: `FileSystem`: Add String `getSystemName()`. `AbstractFakeFileSystem`: Add *systemName* property and default it in WindowsFakeFileSystem ("WINDOWS") and UnixFakeFileSystem ("UNIX"). 
 - #15: `FakeFtpServer`: Use the *systemName* from the configured FileSystem for the SYST command if the *systemName* property is not set on `FakeFtpServer`.  

Infrastructure and Dependencies
 - #7: Upgrade to Java 1.8.
 - #11: Upgrade to commons-net 3.8.0.
 - #8: Upgrade to Groovy 3.0.7.
 - #9: Remove EasyMock dependency. Switch to Mockito.
 - #10: Convert tests to JUnit 5.


Version 2.8.0 (Feb 2021)
------------------------------------------
 - #1: Move project source code to GitHub.
 - #2: Migrate project to Gradle.
 - #3: Change CHANGELOG to markdown.
 - #4: Migrate website files from APT to Google Pages and Markdown.
 - #5: Update dependencies
     - Main - slf4j-api: 1.7.30. 
     - Test - spring: 2.5.6.SEC03; groovy-all: 1.8.9; junit: 4.13.2.
 - #6: Publish Javadocs to javadoc.io. 


Version 2.7.1 (Dec 2016)
------------------------------------------
 - Bug #29: LIST and NLST command handler sends unexpected 150 response for permissions error.


Version 2.7 (Oct 2016)
------------------------------------------
- BUG #28: Error on FakeFtpServer rename. Renaming a directory fails due to descendants returning out of order.
- FEATURE #6: Allow specifying charset (encoding) when creating a FileEntry. Added setContents(String contents, String charset).
INTERNAL CHANGES:
- PortTestUtil: Fix Linux incompatibility; Change default port number from 21 to 62999.
- FakeFtpServerIntegrationTest: Fix Linux incompatibility; Ignore order of returned filenames.
- Clean up javadoc.


Version 2.6 (Apr 2015)
------------------------------------------
- BUG #25: OutOfMemoryError: Java heap space; clean up closed sessions. Session: Added boolean Session.isClosed(). AbstractFtpServer: Clean up closed sessions.
- BUG #24: Close passiveModeDataSocket if in *passive* mode.
- BUG/FEATURE #27: Treat null incoming command as terminating the session.


Version 2.5 (May 2014)
------------------------------------------
- Fix #23 PWD response should have commentary: "{0}" is current directory.
  Also adjusted reply text for MKD to adhere to RFC959: "{0}" created.
- Removed deprecation of assertSessionReply(int,Object)
- Fix broken internal links on the website pages.
- Update “Log4J Configuration Required to See Log Output” section on “FakeFtpServer – Getting Started” with info for SLF4J.
- Added MockFtpServer logo image. Thanks to cooltext.com.


Version 2.4 (15 Jul 2012)
------------------------------------------
- FEATURE #2466395: Remove log4j dependency. Switch to using SLF4J (http://www.slf4j.org/).
- FEATURE #3544349: Return MockFtpServer information as part of connect 220 response.
- Upgrade to Groovy 1.7.10; fix Maven site plugin incompatibility.
- Change “pom.xml” to use SFTP to deploy to Maven repo.


Version 2.3 (05 Jun 2011)
------------------------------------------
- FEATURE #2996739: Use a dynamically chosen free port number ("ephemeral")for the server control port
  if you specify 0 for the serverControlPort property of FakeFtpServer or StubFtpServer. Then call
  getServerControlPort() AFTER start() has been called to determine the actual port number being used.
  This is useful if you are running on a system where the default port (21) is already in use or cannot
  be bound from a user process (such as Unix).
- FEATURE #3304849: Add a new readData(int numBytes) to Session
- BUG #3103132: shutting down takes too long.
 

Version 2.2 (23 Mar 2010)
------------------------------------------
- FakeFtpServer: Support renaming of directories.
    * BUG FIX: #2823519 "The RnfrCommandHandler is currently set to only support renaming of files": https://sourceforge.net/tracker/?func=detail&atid=1006533&aid=2823519&group_id=208647.
    * Change fake RNTO and RNFR CommandHandlers to allow renaming directories.
- BUG FIX: #2828362: "Unit tests using FakeFtpServer are slow" https://sourceforge.net/tracker/?func=detail&atid=1006533&aid=2828362&group_id=208647. DefaultSession.readCommand().
    * Reduce default socket read interval time to 20ms.
- BUG FIX: #2953392: "AbstractFtpServer waits endless if binding to port fails" https://sourceforge.net/tracker/?func=detail&atid=1006533&aid=2953392&group_id=208647.
- FakeFtpServer (AbstractFakeFileSystem): Change rename() to fail if the TO file already exists.
- Add sample directory listing(s) to online docs for StubFtpServer ListCommandHandler. Update online docs/javadoc describing that multiple directory entries in a file listing can be simulated.
- PatternUtil: Support plus sign ('+') within wildcard strings. See convertStringWithWildcardsToRegex().
- TESTS: Rename AbstractTest to AbstractTestCase and AbstractGroovyTest to AbstractGroovyTestCase.


Version 2.1 (16 Jun 2009)
------------------------------------------
- Added support for IPv6 (EPRT and EPSV commands) to FakeFtpServer and StubFtpServer. Thanks to Fernando Martinez for testing.
- BUG FIX: #2696898: �WindowsFakeFilesystem DirectoryEntry case sensitivity� (https://sourceforge.net/tracker/?func=detail&atid=1006533&aid=2696898&group_id=208647).
- BUG FIX: #2797980: �UnixFakeFileSystem IsValidName Regex incorrect� (https://sourceforge.net/tracker/?func=detail&atid=1006533&aid=2797980&group_id=208647).
- Add getServerControlPort() to AbstractFtpServer.
- Create HostAndPort class. Refactor both PortCommandHandler(s) and the PortParser classes to use HostAndPort.
- TESTS: Convert PortParserTest to Groovy.


Version 2.0.2 (09 Mar 2009)
------------------------------------------
- BUG FIX: #2654577: 'month' in UnixDirectoryListingFormatter is Locale specific. http://sourceforge.net/tracker/index.php?func=detail&aid=2654577&group_id=208647&atid=1006533.
- BUG FIX: #2653626: Cannot start() server after calling stop(). https://sourceforge.net/tracker2/index.php?func=detail&aid=2653626&group_id=208647&atid=1006533.


Version 2.0.1 (09 Feb 2009)
------------------------------------------
- BUG FIX: #2543193 �"cd .." and "pwd" don't work properly together� (https://sourceforge.net/tracker2/?func=detail&aid=2543193&group_id=208647&atid=1006533).
- BUG FIX: #2540548 �Missing new line on directory listing� (https://sourceforge.net/tracker2/?func=detail&aid=2540548&group_id=208647&atid=1006533).
- BUG FIX: #2540366 �FileEntry.setContents( byte [] contents ) change the content� (https://sourceforge.net/tracker2/?func=detail&aid=2540366&group_id=208647&atid=1006533).
 - AbstractFtpServer: Use entrySet() to iterate through sessions (From Rijk van Haaften).


Version 2.0 (03 Jan 2009)
------------------------------------------
- BUG FIX: #2462794 filesystem.pathDoesNotExist key is missing from the ReplyText resource bundle. See https://sourceforge.net/tracker2/?func=detail&aid=2462794&group_id=208647&atid=1006533
- BUG FIX: #2462973 FileEntry.cloneWithNewPath doesn't clone out field. See https://sourceforge.net/tracker/index.php?func=detail&aid=2462973&group_id=208647&atid=1006533
- Add note to online doc about requiring Log4J configuration file if you want to see log output.


Version 2.0-rc3 (14 Dec 2008)
------------------------------------------
- BUG FIX: ClassCastException in AbstractFtpServer during server cleanup.
- Reorganize sample code and include in online doc.


Version 2.0-rc2 (12 Dec 2008)
------------------------------------------
- BUG FIX: AbstractFtpServer: Fix bug when iterating through sessions.
- [BREAKING CHANGE] Move ConnectCommandHandler into core package.
- [BREAKING CHANGE] Unify Fake and Stub CommandHandlers. Change ServerConfiguration to remove getReplyTextBundle(); make AbstractFakeCommandHandler implement ReplyTextBundleAware instead. Change FakeFtpServer to check for ReplyTextBundleAware and set replyTextBundle. Pull common from stub/fake into AbstractCommandHandler.
- [BREAKING CHANGE] Rename AbstractCommandHandler to AbstractTrackingCommandHandler.
- Create AbstractStaticReplyCommandHandler, and make both AbstractStubCommandHandler and StaticReplyCommandHandler subclasses.
- Create new UnrecognizedCommandHandler, and use to return 502 reply from FakeFtpServer and StubFtpServer when a requested command is not supported.
- Add support for STAT command; Add systemStatus property to FakeFtpServer.
- Add support for SMNT command to FakeFtpServer;
- AbstractFtpServer: Add createSession() method. Make some attributes protected.
- StubFtpServer: Introduce AbstractStorCommandHandler. Remove final from stub CommandHandler classes.
- Cleanup code and javadoc
- DOCS:	Add �Requirements� section to main (index) page. Also �Maven� section.
- DOCS: Add "Configuring CommandHandler for New (Unsupported) Command" and �Creating Your Own Custom CommandHandler Class� sections to StubFtpServer Getting Started Guide.
- DOCS: Add "Configuring Custom CommandHandlers" section to Getting Started Guide (FakeFtpServer).
- TESTS: Move AbstractCommandHandlerTest into core package.
- TESTS: Create sample test of FakeFtpServer with StaticReplyCommandHandler command handler(s).
- Create source jar during package and include within assemblies.
- Change "assembly.xml" to include "fakeftpserver*.xml" files.


Version 2.0-rc1 (23 Nov 2008)
------------------------------------------
NEW FakeFtpServer.
  This is an alternative "mock" FTP server implementation. FakeFtpServer provides a higher-level abstraction
  than StubFtpServer. You define a virtual file system, including directories and files, as well as a set of
  valid user accounts and credentials. The FakeFtpServer then responds with appropriate replies and reply
  codes based on that configuration. See online documentation for more information.
StubFtpServer
- StubFtpServer: Refactored to inherit from common AbstractFtpServer superclass.
- Change default org.mockftpserver.stub.command.CdupCommandHandler CDUP reply code from 250 to 200.
- Rename ReplyCodes.SEND_DATA_INITIAL_OK and SEND_DATA_FINAL_OK to TRANSFER_DATA_.. indicate bi-directionality.
- Rename Command.getRequiredString(int) to getRequiredParameter(int).
- Change StubFtpServer CommandHandlers to reply with 501 if required command parameters are missing. Changed AbstractCommandHandler and AbstractCommandHandlerTest.
- Refactor (Stub)PortCommandHandler - pull out common logic into PortParser util class.


Version 1.2.4 (01 Sep 2008)
------------------------------------------
- BUG FIX: StubFtpServer: Only execute serverSocket.close() if serverSocket != null.
- BUG FIX: Terminate replies with <CRLF> (\r\n).
- DOCS: Fix Getting Started Guide code example: setOverrideFinalReplyCode() to setFinalReplyCode().
- DOCS: Add note to Getting Started Guide about calling setServerControlPort() if on Unix system.


Version 1.2.3 (13 Aug 2008)
------------------------------------------
- BUG FIX: Tracker item #2047355. Parse host IP numbers as unsigned bytes.
  See https://sourceforge.net/tracker/index.php?func=detail&aid=2047355&group_id=208647&atid=1006533


Version 1.2.2 (27 May 2008)
------------------------------------------
- BUG FIX: Move serverThread.start() into synchronized block to avoid server hang if
  server thread runs faster than main thread.
  See https://sourceforge.net/tracker/?func=detail&atid=1006533&aid=1925590&group_id=208647


Version 1.2.1 (10 Mar 2008)
------------------------------------------
- Change Maven POM (pom.xml) to enable sync-ing with central Maven repository (ibiblio).

Version 1.2 (29 Feb 2008)
------------------------------------------
- BUG FIX: StubFtpServer: Add wait/notify to ensure that the server starts up and opens the server 
  control port before the start() method returns. This fixes a potential race condition, which
  shows up on some Linux systems. (Thanks to Aasman Bajaj for identifying the problem and providing the fix)
- Modify tests to make server port configurable (through "ftp.server.port" system property), allowing 
  tests to run on non-Windows systems. 


Version 1.1 (20 Feb 2008)
------------------------------------------
- StubFtpServer: Allow configuring server control connection port other than the default (21).
- AbstractTest: Add some test convenience methods.


Version 1.0 final (11 Dec 2007)
------------------------------------------
- Implement default CommandHandlers for NLST, REIN, SMNT, SITE, ABOR and ALLO commands.
- Handle command names in any case.
- CwdCommandHandler: Fix PATHNAME_KEY constant value; change to "pathname".


Version 1.0-RC1 (1 Nov 2007)
---------------------------------------
Initial release.