/*
 * Copyright 2021 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mockftpserver.stub.command;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockftpserver.core.command.AbstractCommandHandlerTestCase;
import org.mockftpserver.core.command.Command;
import org.mockftpserver.core.command.CommandNames;
import org.mockftpserver.core.command.ReplyCodes;
import org.mockftpserver.core.util.AssertFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests for the RetrCommandHandler class
 *
 * @author Chris Mair
 */
class RetrCommandHandlerTest extends AbstractCommandHandlerTestCase {

    private static final Logger LOG = LoggerFactory.getLogger(RetrCommandHandlerTest.class);

    private RetrCommandHandler commandHandler;

    @Test
    void testConstructor_String_Null() {
        try {
            new RetrCommandHandler((String) null);
            fail("Expected AssertFailedException");
        }
        catch (AssertFailedException expected) {
            LOG.info("Expected: " + expected);
        }
    }

    @Test
    void testConstructor_ByteArray_Null() {
        try {
            new RetrCommandHandler((byte[]) null);
            fail("Expected AssertFailedException");
        }
        catch (AssertFailedException expected) {
            LOG.info("Expected: " + expected);
        }
    }

    @Test
    void testSetFileContents_String_Null() {
        try {
            commandHandler.setFileContents((String) null);
            fail("Expected AssertFailedException");
        }
        catch (AssertFailedException expected) {
            LOG.info("Expected: " + expected);
        }
    }

    @Test
    void testSetFileContents_ByteArray_Null() {
        try {
            commandHandler.setFileContents((byte[]) null);
            fail("Expected AssertFailedException");
        }
        catch (AssertFailedException expected) {
            LOG.info("Expected: " + expected);
        }
    }

    @Test
    void testHandleCommand() throws Exception {
        final String FILE_CONTENTS = "abc_123 456";
        commandHandler.setFileContents(FILE_CONTENTS);

        Command command = new Command(CommandNames.RETR, array(FILENAME1));
        commandHandler.handleCommand(command, session);

        verify(session).sendReply(ReplyCodes.TRANSFER_DATA_INITIAL_OK, replyTextFor(ReplyCodes.TRANSFER_DATA_INITIAL_OK));
        verify(session).openDataConnection();
        verify(session).sendData(FILE_CONTENTS.getBytes(), FILE_CONTENTS.length());
        verify(session).closeDataConnection();
        verify(session).sendReply(ReplyCodes.TRANSFER_DATA_FINAL_OK, replyTextFor(ReplyCodes.TRANSFER_DATA_FINAL_OK));

        verifyNumberOfInvocations(commandHandler, 1);
        verifyOneDataElement(commandHandler.getInvocation(0), RetrCommandHandler.PATHNAME_KEY, FILENAME1);
    }

    @Test
    void testHandleCommand_MissingPathnameParameter() throws Exception {
        testHandleCommand_InvalidParameters(commandHandler, CommandNames.RETR, EMPTY);
    }

    @BeforeEach
    void setUp() throws Exception {
        commandHandler = new RetrCommandHandler();
        commandHandler.setReplyTextBundle(replyTextBundle);
    }

}
