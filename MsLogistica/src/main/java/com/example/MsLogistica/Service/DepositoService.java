package com.example.MsLogistica.Service;

import com.example.MsLogistica.Models.Deposito;
import com.example.MsLogistica.Repository.DepositoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositoService {

    private final DepositoRepository depositoRepository;

    public DepositoService(DepositoRepository depositoRepository) {
        this.depositoRepository = depositoRepository;
    }
    public List<Deposito> buscarTodos() {
        return depositoRepository.findAll();
    }
    public Deposito crearDeposito(Deposito deposito) {
        return depositoRepository.save(deposito);
    }
}