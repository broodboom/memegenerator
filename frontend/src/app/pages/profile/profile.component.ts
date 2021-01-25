import { Component, OnInit } from "@angular/core";
import { User } from "app/models/User";
import { AuthService } from "app/services/auth/auth.service";
import { ProfileService } from "../../services/profile/profile.service";

let self: any;

@Component({
  selector: "ngx-profile",
  templateUrl: "./profile.component.html",
  styleUrls: ["./profile.component.scss"],
})
export class ProfileComponent implements OnInit {
  constructor(private profileService: ProfileService, private authService: AuthService) {}

  user: User;

  ngOnInit(): void {
    self = this;

    this.profileService.getUserInfo(this.authService.getCurrentUser().id).subscribe(user => {this.user = user});
  }

  updateUser(): void {
    const e: Event = window.event;
    e.preventDefault();

    self.user.email = (<HTMLInputElement>(
      document.getElementById("email")
    )).value;
    self.user.password = (<HTMLInputElement>(
      document.getElementById("password")
    )).value;

    this.profileService.updateUserInfo(this.user).subscribe();
  }
}
