package guru.sfg.beer.order.service.services;

import guru.sfg.brewery.model.CustomerPagedList;
import org.springframework.data.domain.PageRequest;

public interface CustomerService {
    CustomerPagedList listCustomers(PageRequest pageable);
}
