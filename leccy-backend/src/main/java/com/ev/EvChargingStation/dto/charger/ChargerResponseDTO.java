package com.ev.EvChargingStation.dto.charger;

import com.ev.EvChargingStation.entity.Charger;
import com.ev.EvChargingStation.enums.ChargerStatus;
import com.ev.EvChargingStation.enums.ConnectorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargerResponseDTO {

    private Long id;
    private String chargerNumber;
    private ConnectorType connectorType;
    private Double outputPower;
    private ChargerStatus chargerStatus;
    private Long stationId;
    private String stationName;
}
