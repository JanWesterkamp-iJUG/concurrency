/*
 * Copyright (c) 2013, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package ee.jakarta.tck.concurrent.common.fixed.counter;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.util.concurrent.atomic.AtomicInteger;

import ee.jakarta.tck.concurrent.framework.TestConstants;
import ee.jakarta.tck.concurrent.framework.junit.extensions.Wait;

public class StaticCounter {

    private static AtomicInteger count = new AtomicInteger(0);

    public static int getCount() {
        return count.get();
    }

    public static void inc() {
        count.incrementAndGet();
    }

    public static void reset() {
        count.set(0);
    }
    
    public static void waitTill(int expected) {
        assertTimeoutPreemptively(TestConstants.WaitTimeout, () -> {
            for(; expected != StaticCounter.getCount(); Wait.sleep(TestConstants.PollInterval));
        });
    }
    
    public static void waitTillSurpassed(int expected) {
        assertTimeoutPreemptively(TestConstants.WaitTimeout, () -> {
            for(; expected <= StaticCounter.getCount(); Wait.sleep(TestConstants.PollInterval));
        });
    }

}
