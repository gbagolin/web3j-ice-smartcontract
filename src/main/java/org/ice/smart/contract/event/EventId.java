package org.ice.smart.contract.event;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;

import java.util.Arrays;
import java.util.List;

public class EventId extends Event {
    private static final String NAME = "EVENT_ID";
    private static final List<TypeReference<?>> PARAMETERS_LIST = Arrays.asList(
            new TypeReference<Uint256>() {
            });

    public EventId() {
        super(NAME, PARAMETERS_LIST);
    }
}
