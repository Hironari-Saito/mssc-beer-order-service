package guru.sfg.beer.order.service.services.listeners;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.beer.order.service.services.BeerOrderManager;
import guru.sfg.brewery.model.events.AllocateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderAllocationResultListener {

    private final BeerOrderManager beerOrdermanager;


    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE)
    public void listen(AllocateOrderResult result ) {
        if(!result.getAllocationError() && !result.getPendingInventory()) {
            // allocated normally
            beerOrdermanager.beerOrderAllocationPassed(result.getBeerOrderDto());
        } else if (!result.getAllocationError() && result.getPendingInventory()) {
            // pending inventory
            beerOrdermanager.beerOrderAllocationPendingInventory(result.getBeerOrderDto());
        } else if (result.getAllocationError()) {
            // allocate error
            beerOrdermanager.beerOrderAllocationFailed(result.getBeerOrderDto());
        }
    }
}
