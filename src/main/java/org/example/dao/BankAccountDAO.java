package org.example.dao;

import org.example.entity.BankAccount;
import org.example.exception.BankTransactionException;
import org.example.model.BankAccountInfo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class BankAccountDAO {
    @Autowired
    private SessionFactory sessionFactory;

    //empty constructor
    public BankAccountDAO() {
    }


    //try delete .this
    public BankAccount findById(Long id) {
        Session session = this.sessionFactory.getCurrentSession();
        BankAccount bankAccount = session.get(BankAccount.class, id);
        return bankAccount;
    }

    public List<BankAccountInfo> listBankAccountInfo() {
        String sql = "Select new " + BankAccountInfo.class.getName() +
                "(e.id,e.fullName,e.balance) " + "from " + BankAccount.class.getName() + " e ";
        Session session = this.sessionFactory.getCurrentSession();
        Query<BankAccountInfo> query = session.createQuery(sql, BankAccountInfo.class);
        return query.getResultList();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void addAmount(Long id, double amount) throws BankTransactionException {
        BankAccount account = this.findById(id);
        if (account == null) {
            throw new BankTransactionException("Account not found " + id);
        }
        double newBalance = account.getBalance() + amount;
        if (account.getBalance() + amount < 0) {
            throw new BankTransactionException(
                    "The money in the account '" + id + "' is not enough (" + account.getBalance() + ")");
        }
        account.setBalance(newBalance);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
    public void sendMoney(Long fromAccountId, Long toAccountId, double amount) throws BankTransactionException {
        addAmount(toAccountId, amount);
        addAmount(fromAccountId, -amount);
    }

}
