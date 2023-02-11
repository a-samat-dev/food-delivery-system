package kz.samat.fooddeliveryservice.repository;

import kz.samat.fooddeliveryservice.model.entity.TicketEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link TicketEntity}
 *
 * Created by Samat Abibulla on 2023-02-10
 */
@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {

    @Query(value = "select t.* from tickets t" +
            "         left join deliveries d on t.id = d.ticket_id " +
            "where d.delivery_status in ('RECEIVED', 'PREPARING') " +
            "order by t.priority desc;",
            nativeQuery = true)
    Page<TicketEntity> findTicketsSortedByPriority(Pageable pageable);
}
