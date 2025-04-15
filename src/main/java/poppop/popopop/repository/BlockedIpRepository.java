package poppop.popopop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poppop.popopop.entity.BlockedIp;

@Repository
public interface BlockedIpRepository extends JpaRepository<BlockedIp, String> {
    boolean existsByIp(String ip);
}
