package com.intuit.complaintservice.controller;

import com.intuit.complaintservice.dto.ComplaintExceptionResponse;
import com.intuit.complaintservice.dto.ComplaintRequest;
import com.intuit.complaintservice.dto.ComplaintResponse;
import com.intuit.complaintservice.dto.FullComplaintResponse;
import com.intuit.complaintservice.exception.ComplaintException;
import com.intuit.complaintservice.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/complaint")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComplaintResponse createComplaint(@RequestBody ComplaintRequest complaintRequest){
        return complaintService.createComplaint(complaintRequest);
    }

    @GetMapping("/{complaintId}")
    @ResponseStatus(HttpStatus.OK)
    public FullComplaintResponse getComplaint(@PathVariable UUID complaintId){
        return complaintService.getComplaint(complaintId);
    }

    // In the time I had, I handled - not found(404) and bad request(400)
    // for other types of exceptions, implementation is required
    @ExceptionHandler()
    private ResponseEntity<ComplaintExceptionResponse> handleException(ComplaintException ex){
        ComplaintExceptionResponse errorResponse = new ComplaintExceptionResponse(
                ex.getStatus().value(), ex.getMessage()
        );

        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }
}
