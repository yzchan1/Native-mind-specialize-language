/*
 * Copyright (c) 2012, 2018, Oracle and/or its affiliates. All rights reserved.
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
package com.github.wooyme.nmsl.nodes.controlflow;

import com.github.wooyme.nmsl.nodes.SLExpressionNode;
import com.github.wooyme.nmsl.nodes.SLRootNode;
import com.github.wooyme.nmsl.nodes.SLStatementNode;
import com.github.wooyme.nmsl.runtime.SLNull;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;

/**
 * The body of a user-defined SL function. This is the node referenced by a {@link SLRootNode} for
 * user-defined functions. It handles the return value of a function: the {@link SLReturnNode return
 * statement} throws an {@link SLReturnException exception} with the return value. This node catches
 * the exception. If the method ends without an explicit {@code return}, return the
 * {@link SLNull#SINGLETON default null value}.
 */
@NodeInfo(shortName = "body")
public final class SLFunctionBodyNode extends SLExpressionNode {

    /** The body of the function. */
    @Child private SLStatementNode bodyNode;
    private final String functionName;
    /**
     * Profiling information, collected by the interpreter, capturing whether the function had an
     * {@link SLReturnNode explicit return statement}. This allows the compiler to generate better
     * code.
     */

    public SLFunctionBodyNode(SLStatementNode bodyNode,String functionName) {
        this.bodyNode = bodyNode;
        this.functionName = functionName;
        addRootTag();
    }

    @Override
    public String generate() {
        return "public class Function"+functionName+"{"
                +"";
    }
}
