package org.ice.smart.contract.event;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;


public class EventManager {
    private TransactionReceipt transactionReceipt;
    private Event event;

    /**
     *
     * @param transactionReceipt
     * @param event
     */
    public EventManager(TransactionReceipt transactionReceipt, Event event) {
        this.transactionReceipt = transactionReceipt;
        this.event = event;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public TransactionReceipt getTransactionReceipt() {
        return this.transactionReceipt;
    }

    public void setTransactionReceipt(TransactionReceipt transactionReceipt) {
        this.transactionReceipt = transactionReceipt;
    }

    /**
     *
     * @return
     */
    public List<Type> getEventParameters() {
        List<Log> logs = transactionReceipt.getLogs();
        Log log = logs.get(0);

        List<Type> results =
                FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());
        return results;
    }

}
