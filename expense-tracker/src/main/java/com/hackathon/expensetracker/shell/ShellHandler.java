package com.hackathon.expensetracker.shell;

import com.hackathon.expensetracker.service.ExpenseService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ShellHandler {

    private final ExpenseService expenseService;

    public ShellHandler(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @ShellMethod(key = "set", value = "set budget amount")
    public String setBudgetAmount(
            @ShellOption(help = "budget amount") int budget
    ) {
        return expenseService.setBudget(budget);
    }

    @ShellMethod(key = "add", value = "add an expense")
    public String addExpense(
            @ShellOption(help = "description of expense") String description,
            @ShellOption(help = "amount of expense") int amount
    ) {
        return expenseService.addExpense(description, amount);
    }

    @ShellMethod(key = "list", value = "list the expense")
    public String listExpense() {
        return expenseService.listExpenses();
    }

    @ShellMethod(key = "delete", value = "delete and expense")
    public String deleteExpense(
            @ShellOption(help = "id of expense") int id
    ) {
        return expenseService.deleteExpense(id);
    }

    @ShellMethod(key = "summary", value = "summarize the expenses")
    public String summarizeExpenses(
            @ShellOption(help = "optional: provide month number", defaultValue = "0") int month
    ) {
        if(month == 0) return expenseService.summarizeExpenses();
        return expenseService.summarizeExpenses(month);
    }
}
