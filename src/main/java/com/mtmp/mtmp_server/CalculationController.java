package com.mtmp.mtmp_server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/calculation", consumes = MediaType.APPLICATION_JSON_VALUE)
public class CalculationController {
    private static final Logger logger = LoggerFactory.getLogger(CalculationController.class);
    @Autowired
    private CalculationService service;

    @PostMapping("/")
    public ResponseEntity<?> calculateData(
            @RequestBody CalculationRequest request
    ){
        try {
            logger.info(
                    "Received calculation request with angle:"
                    + request.getAngle()
                    + ", velocity:" + request.getVelocity()
            );
            logger.info("Responding with calculated data...");
            return ResponseEntity.status(HttpStatus.OK).body(
                    this.service.calculateData(request)
            );
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Somethig went wrong.");
        }
    }
}
