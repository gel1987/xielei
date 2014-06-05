
package com;
/*
 * Copyright (C) 2012 The Android Open Source Project
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
import com.google.android.vending.expansion.downloader.impl.DownloaderService;

/**
 * This class demonstrates the minimal client implementation of the DownloaderService from
 * the Downloader library.  Since services must be uniquely registered across all of Android
 * it's a good idea for services to reside directly within your Android application package.
 */
public class SampleDownloaderService extends DownloaderService {
    // stuff for LVL -- MODIFY FOR YOUR APPLICATION!
    private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnW8pXYxd+Z962h6QqILQGo0ig7cBbKklqO56Px4F8X0wlEENyoxPu/iQulFOGJhzAlaTOVAiYj8SJvfPZfpA1kO02cQxiFs/FTynKZnWlxXkXicesPR3b5yG9oxr1aedtoqfKaqjXMjhSHi/w2HJu0whtpMmg70taBzMSMgl0tk6TK8TLe9/QgAI+Hih5DC4SAAGCdHkWJTZUX74Vp0kx7QTWwwayCuVJl6yzbGWZO444pa7k3QJ6VAxGNmciO0NjhwOWfhcvMgMiSFmDXLO60oK581YdDvE9DJYFj0x0Ndkd5S43jrta4HWes25W9vM492Etn3hmtQx9KrOGs8TlwIDAQAB";
    // used by the preference obfuscater
    private static final byte[] SALT = new byte[] {
            1, 43, -12, -11, 4, 8,
            -100, -12, 43, 12, -8, -4, 9, 5, -106, -108, -33, 45, -1, 84
    };

    /**
     * This public key comes from your Android Market publisher account, and it
     * used by the LVL to validate responses from Market on your behalf.
     */
    @Override
    public String getPublicKey() {
        return BASE64_PUBLIC_KEY;
    }

    /**
     * This is used by the preference obfuscater to make sure that your
     * obfuscated preferences are different than the ones used by other
     * applications.
     */
    @Override
    public byte[] getSALT() {
        return SALT;
    }

    /**
     * Fill this in with the class name for your alarm receiver. We do this
     * because receivers must be unique across all of Android (it's a good idea
     * to make sure that your receiver is in your unique package)
     */
    @Override
    public String getAlarmReceiverClassName() {
        return SampleAlarmReceiver.class.getName();
    }

}
