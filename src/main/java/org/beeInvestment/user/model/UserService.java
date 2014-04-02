package org.beeInvestment.user.model;

public interface UserService {
void register();
void login();
void logout();
void resetPassword();
void changePassword();
void updateProfile();
void freezeUser();
}
