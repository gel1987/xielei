package com.donate.gf;

interface PayCallback {
	void paySucess(in String item);

	void payFailed(in String item);
}