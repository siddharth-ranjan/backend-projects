package com.hackathon.expensetracker.service;

import com.hackathon.expensetracker.model.Expense;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private int budget = -1;
    private List<Expense> expenses = new ArrayList<>();
    private int maxId = 0;

    public String setBudget(int budget) {
        String response;
        if(this.budget < 0) {
            this.budget = budget;
            response = "Budget set successfully!";
        } else {
            response = "Failed! Budget already set!";
        }
        return response;
    }

    public String listExpenses() {
        if (this.budget < 0) {
            return "Error: Budget not set! Please set a budget before adding expenses.";
        }
        StringBuilder response = new StringBuilder("ID  Date      Description  Amount");
        for(Expense expense : expenses) {
            response.append("\n").append(expense.getId()).append("   ").append(expense.getDate()).append("   ").append(expense.getDescription()).append("   ").append(expense.getAmount());
        }
        return response.toString();
    }


    public String addExpense(String description, int amount) {
        if (this.budget < 0) {
            return "Error: Budget not set! Please set a budget before adding expenses.";
        }
        Expense expense = new Expense(incrementAndGetId(), LocalDate.now(), description, amount);
        expenses.add(expense);
        String response = "Expense added successfully (ID: " + expense.getId() +")";
        if(!underBudget()) {
            response += "\nYou are over budget!!";
        }
        return response;
    }

    public String deleteExpense(int id) {
        if (this.budget < 0) {
            return "Error: Budget not set! Please set a budget before adding expenses.";
        }
        Expense expense = null;
        String response;
        for(Expense expense2 : expenses) {
            if(expense2.getId() == id) {
                expense = expense2;
                break;
            }
        }
        if(expense != null) {
            int tmpId = expense.getId();
            expenses.remove(expense);
            response = "Expense deleted successfully (ID: " + tmpId + ")";
        }
        else {
            response = "Expense not found!";
        }
        return response;
    }

    public String summarizeExpenses() {
        if (this.budget < 0) {
            return "Error: Budget not set! Please set a budget before adding expenses.";
        }
        int totalExpenses = 0;
        for(Expense expense : expenses) {
            totalExpenses += expense.getAmount();
        }
        return "Total Expenses for month : " + totalExpenses;
    }

    public String summarizeExpenses(int month) {
        if (this.budget < 0) {
            return "Error: Budget not set! Please set a budget before adding expenses.";
        }
        if (month < 1 || month > 12) {
            return "Invalid month! Please provide a valid month (1-12).";
        }

        int totalExpenses = 0;
        for(Expense expense : expenses) {
            if(month == expense.getDate().getMonthValue())totalExpenses += expense.getAmount();
        }
        return "Total Expenses for month " + month + ": " + totalExpenses;
    }

    private int incrementAndGetId() {
        return ++this.maxId;
    }

    private boolean underBudget() {
        int currentBudget = this.budget;
        int totalSpent = 0;

        for(Expense expense : this.expenses) {
            totalSpent += expense.getAmount();
        }
        return currentBudget >= totalSpent;
    }

}
