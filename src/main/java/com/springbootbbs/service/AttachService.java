package com.springbootbbs.service;

import com.springbootbbs.entiry.Attach;
import com.springbootbbs.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AttachService {

    @Autowired
    AttachRepository attachRepository;

    public Attach save(Attach attach) {
        Date date = new Date();

        attach.setUpdatedAt(date);

        Boolean uploadSuccess = attach.upload();

        if (uploadSuccess) {
            return attachRepository.save(attach);
        } else {
            return null;
        }
    }

    public Boolean delete(Attach attach) {
        attach.deleteFile();
        attachRepository.delete(attach);
        return true;
    }

}
