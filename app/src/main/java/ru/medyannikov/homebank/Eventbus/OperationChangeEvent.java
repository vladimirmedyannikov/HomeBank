package ru.medyannikov.homebank.Eventbus;

import java.util.List;

import ru.medyannikov.homebank.Model.Operation;

/**
 * Created by Vladimir on 20.10.2015.
 */
public final class OperationChangeEvent {
    private List<Operation> operationList;

    public List<Operation> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<Operation> operationList) {
        this.operationList = operationList;
    }
}
