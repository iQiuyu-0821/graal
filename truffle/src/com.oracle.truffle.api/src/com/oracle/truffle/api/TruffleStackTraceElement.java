/*
 * Copyright (c) 2017, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * The Universal Permissive License (UPL), Version 1.0
 * 
 * Subject to the condition set forth below, permission is hereby granted to any
 * person obtaining a copy of this software, associated documentation and/or
 * data (collectively the "Software"), free of charge and under any and all
 * copyright rights in the Software, and any and all patent rights owned or
 * freely licensable by each licensor hereunder covering either (i) the
 * unmodified Software as contributed to or provided by such licensor, or (ii)
 * the Larger Works (as defined below), to deal in both
 * 
 * (a) the Software, and
 * 
 * (b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
 * one is included with the Software each a "Larger Work" to which the Software
 * is contributed by such licensors),
 * 
 * without restriction, including without limitation the rights to copy, create
 * derivative works of, display, perform, and distribute the Software and make,
 * use, sell, offer for sale, import, export, have made, and have sold the
 * Software and the Larger Work(s), and to sublicense the foregoing rights on
 * either these or other terms.
 * 
 * This license is subject to the following condition:
 * 
 * The above copyright notice and either this complete permission notice or at a
 * minimum a reference to the UPL must be included in all copies or substantial
 * portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.oracle.truffle.api;

import java.util.List;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;

/**
 * Represents a guest stack trace element.
 *
 * @since 0.27
 */
public final class TruffleStackTraceElement {

    private final Node location;
    private final RootCallTarget target;
    private final Frame frame;

    TruffleStackTraceElement(Node location, RootCallTarget target, Frame frame) {
        this.location = location;
        this.target = target;
        this.frame = frame;
    }

    /**
     * Returns a node representing the callsite on the stack. Returns <code>null</code> if no
     * detailed callsite information is available.
     *
     * @since 0.27
     **/
    public Node getLocation() {
        return location;
    }

    /**
     * Returns the call target on the stack. Returns never <code>null</code>.
     *
     * @since 0.27
     **/
    public RootCallTarget getTarget() {
        return target;
    }

    /**
     * Returns the materialized frame. Returns <code>null</code> if the initial {@link RootNode}
     * that filled in the stack trace did not request frames to be captured by overriding
     * {@link RootNode#isCaptureFramesForTrace()}.
     *
     * @since 0.31
     */
    public Frame getFrame() {
        return frame;
    }

    /**
     * Returns the guest language frames that are stored in this throwable or <code>null</code> if
     * no guest language frames are available. Guest language frames are automatically added by the
     * Truffle runtime the first time the exception is passed through a {@link CallTarget call
     * target} and the frames are not yet set. Therefore no guest language frames are available
     * immediately after the exception was constructed. The returned list is not modifiable. The
     * number stack trace elements that are filled in can be customized by implementing
     * {@link TruffleException#getStackTraceElementLimit()} .
     *
     * @param throwable the throwable instance to look for guest language frames
     * @see #fillIn(Throwable) To force early filling of guest language stack frames.
     * @since 0.27
     */
    public static List<TruffleStackTraceElement> getStackTrace(Throwable throwable) {
        return TruffleStackTrace.find(throwable);
    }

    /**
     * Fills in the guest language stack frames from the current frames on the stack. If the stack
     * was already filled before then this method has no effect. The implementation attaches a
     * lightweight exception object to the last location in the {@link Throwable#getCause() cause}
     * chain of the exception. The number stack trace elements that are filled in can be customized
     * by implementing {@link TruffleException#getStackTraceElementLimit()} .
     *
     * @param throwable the throwable to fill
     * @since 0.27
     */
    public static void fillIn(Throwable throwable) {
        TruffleStackTrace.fillIn(throwable);
    }

}
