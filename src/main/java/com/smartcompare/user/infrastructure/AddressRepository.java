package com.smartcompare.user.infrastructure;

import com.smartcompare.user.domain.Address;
import com.smartcompare.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
}

