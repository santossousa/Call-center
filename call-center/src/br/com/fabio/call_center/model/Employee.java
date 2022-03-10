package br.com.fabio.call_center.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Employee implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(Employee.class);

	private EmployeeType employeeType;
	private EmployeeStatus employeeStatus;

	private ConcurrentLinkedDeque<Call> incomingCalls;
	private ConcurrentLinkedDeque<Call> attendedCalls;

	public Employee(EmployeeType employeeType) {
		super();
		this.employeeType = employeeType;
		this.employeeStatus = EmployeeStatus.AVAILABLE;
		this.incomingCalls = new ConcurrentLinkedDeque<>();
		this.attendedCalls = new ConcurrentLinkedDeque<>();
	}

	public EmployeeType getEmployeeType() {
		return employeeType;
	}

	public synchronized EmployeeStatus getEmployeeStatus() {
		return employeeStatus;
	}

	private synchronized void setEmployeeState(EmployeeStatus employeeStatus) {
		logger.info("O funcionário " + Thread.currentThread().getName() + " mudou seu estado para " + employeeStatus);
		this.employeeStatus = employeeStatus;
	}

	public synchronized List<Call> getAttendedCalls() {
		return new ArrayList<>(attendedCalls);
	}

	public synchronized void attend(Call call) {
		logger.info("O funcionário " + Thread.currentThread().getName() + " está em uma chamada de  "
				+ call.getDuration() + " segundos");
		this.incomingCalls.add(call);
	}

	public static Employee buildAttendant() {
		return new Employee(EmployeeType.ATTENDANT);
	}

	public static Employee buildMnager() {
		return new Employee(EmployeeType.MANAGER);
	}

	public static Employee buildDirector() {
		return new Employee(EmployeeType.DIRECTOR);
	}

	@Override
	public void run() {
		logger.info("O funcionário " + Thread.currentThread().getName() + " começou a trabalhar");

		while (true) {
			if (!this.incomingCalls.isEmpty()) {
				Call call = this.incomingCalls.poll();
				this.setEmployeeState(EmployeeStatus.BUSY);
				logger.info("O funcionário " + Thread.currentThread().getName() + " está em uma chamada de"
						+ call.getDuration() + " segundos");
				try {
					TimeUnit.SECONDS.sleep(call.getDuration());
				} catch (InterruptedException e) {
					logger.error("O funcionário " + Thread.currentThread().getName()
							+ " foi interrompido e não conseguiu terminar a chamada de " + call.getDuration()
							+ " segundos");
				} finally {
					this.setEmployeeState(EmployeeStatus.AVAILABLE);
				}
				this.attendedCalls.add(call);
				logger.info("O funcionário " + Thread.currentThread().getName() + " terminou uma ligação de "
						+ call.getDuration() + " segundos");
			}
			// TODO Auto-generated method stub

		}

	}
}
