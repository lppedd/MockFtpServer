/*
 * Copyright 2008 the original author or authors.
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
package org.mockftpserver.fake.command

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockftpserver.core.command.Command
import org.mockftpserver.core.command.CommandHandler
import org.mockftpserver.core.command.CommandNames
import org.mockftpserver.core.command.ReplyCodes
import org.mockftpserver.core.session.SessionKeys
import org.mockftpserver.fake.filesystem.Permissions

/**
 * Tests for CdupCommandHandler
 *
 * @author Chris Mair
 */
class CdupCommandHandlerTest extends AbstractFakeCommandHandlerTestCase {

    def DIR = "/usr"
    def SUBDIR = "${DIR}/sub"

    @Test
    void testHandleCommand() {
        setCurrentDirectory(SUBDIR)
        handleCommand([])
        assertSessionReply(ReplyCodes.CDUP_OK, ['cdup', DIR])
        assert session.getAttribute(SessionKeys.CURRENT_DIRECTORY) == DIR
    }

    @Test
    void testHandleCommand_NoParentDirectory() {
        setCurrentDirectory('/')
        handleCommand([])
        assertSessionReply(ReplyCodes.READ_FILE_ERROR, ['filesystem.parentDirectoryDoesNotExist', '/'])
        assert session.getAttribute(SessionKeys.CURRENT_DIRECTORY) == '/'
    }

    @Test
    void testHandleCommand_NoExecuteAccessToDirectory() {
        setCurrentDirectory(SUBDIR)
        def dir = fileSystem.getEntry(DIR)
        dir.permissions = new Permissions('rw-rw-rw-')
        handleCommand([])
        assertSessionReply(ReplyCodes.READ_FILE_ERROR, ['filesystem.cannotExecute', DIR])
        assert session.getAttribute(SessionKeys.CURRENT_DIRECTORY) == SUBDIR
    }

    //-------------------------------------------------------------------------
    // Helper Methods
    //-------------------------------------------------------------------------

    CommandHandler createCommandHandler() {
        new CdupCommandHandler()
    }

    Command createValidCommand() {
        return new Command(CommandNames.CDUP, [])
    }

    @BeforeEach
    void setUp() {
        createDirectory(SUBDIR)
    }

}