/*
 * Copyright (C) 2011 Jason von Nieda <jason@vonnieda.org>
 * 
 * This file is part of OpenPnP.
 * 
 * OpenPnP is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * OpenPnP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with OpenPnP. If not, see
 * <http://www.gnu.org/licenses/>.
 * 
 * For more information about OpenPnP visit http://openpnp.org
 */

package org.openpnp.gui.support;

import java.awt.Component;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

import org.openpnp.gui.MainFrame;
import org.pmw.tinylog.Logger;

public class MessageBoxes {

    // prepare message for use in a message box
    static String prepareMessage(String message) {
        if (message == null) {
            message = "";
        }
        message = message.replaceAll("\n", "<br/>");
        message = message.replaceAll("\r", "");
        message = "<html><body width=\"400\">" + message + "</body></html>";
        return message;
    }    

    public static boolean errorBox(Component parent, String title, Throwable cause, boolean withContinuation) {
        String message = null;
        boolean ret = false;
        if (cause != null) {
            message = cause.getMessage();
            if (message == null || message.trim().isEmpty()) {
                StringWriter stringWriter = new StringWriter();
                PrintWriter writer = new PrintWriter(stringWriter);
                cause.printStackTrace(writer);
                writer.close();
                message = stringWriter.toString();
            }
        }
        if (message == null) {
            message = "No message supplied.";
        }
        Logger.debug("{}: {}", title, cause);
        message = message.replaceAll("<", "&lt;");
        message = message.replaceAll(">", "&gt;");
        message = prepareMessage(message);

        // if this errorBox shall ask for Continuation, show a ConfirmDialog and return if the user selected YES
        if (withContinuation) {
            ret = JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.OK_OPTION;
        } else {
            JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
        }
        
        return ret;
    }

    public static void errorBox(Component parent, String title, Throwable cause) {
        errorBox(parent, title, cause, false);
    }

    public static void errorBox(Component parent, String title, String message) {
        if (message == null) {
            message = "";
        }
        Logger.debug("{}: {}", title, message);
        message = prepareMessage(message);
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static boolean errorBoxWithRetry(Component parent, String title, String message) {
        if (message == null) {
            message = "";
        }
        Logger.debug("{}: {}", title, message);
        message = prepareMessage(message);
        return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static void infoBox(String title, String message) {
        if (message == null) {
            message = "";
        }
        message = prepareMessage(message);
        JOptionPane.showMessageDialog(MainFrame.get(), message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void notYetImplemented(Component parent) {
        errorBox(parent, "Not Yet Implemented", "This function is not yet implemented.");
    }
}
