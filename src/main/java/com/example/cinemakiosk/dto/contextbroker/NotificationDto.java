package com.example.cinemakiosk.dto.contextbroker;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotificationDto<T> extends BaseEntityDto {
    private String subscriptionId;
    private Date notifiedAt;
    private List<T> data;
}
