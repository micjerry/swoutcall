package com.lige.call.impl.tools;

import java.util.ArrayList;
import java.util.List;

import com.lige.call.api.cmd.SwCallReceipt;

public class ReceiptLoader {
	public static List<SwCallReceipt> loadReceipt(SwCallReceipt receipt) {
		List<SwCallReceipt> receipts = new ArrayList<SwCallReceipt>();
		receipts.add(receipt);
		return receipts;
	}
}
