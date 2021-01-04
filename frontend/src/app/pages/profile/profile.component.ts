import { Component, OnInit } from "@angular/core";
import { User } from "app/models/User";
import { ProfileService } from "../../services/profile.service";

let self: any;

@Component({
  selector: "ngx-profile",
  templateUrl: "./profile.component.html",
  styleUrls: ["./profile.component.scss"],
})
export class ProfileComponent implements OnInit {
  constructor(private profileService: ProfileService) {}

  user: User;

  ngOnInit(): void {
    self = this;

    this.profileService.getUserInfo();
  }

  updateUser(): void {
    const e: Event = window.event;
    e.preventDefault();

    this.profileService.updateUserInfo(this.user).subscribe(() => {
      self.user.email = (<HTMLInputElement>(
        document.getElementById("email")
      )).value;
      self.user.password = (<HTMLInputElement>(
        document.getElementById("password")
      )).value;
    });
  }
}
