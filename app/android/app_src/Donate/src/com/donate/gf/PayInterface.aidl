package com.donate.gf;

import com.donate.gf.PayCallback;

interface PayInterface{
	void pay(in PayCallback cb,in String item,in int minFee);
}