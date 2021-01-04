import { Component, OnInit, OnDestroy } from "@angular/core";
import { NbMenuItem } from "@nebular/theme";
import { AuthService } from "app/services/auth.service";
import {
  Observable,
  BehaviorSubject,
  Subject,
  Subscription,
} from "rxjs";

@Component({
  selector: "ngx-pages",
  styleUrls: ["pages.component.scss"],
  template: `
    <ngx-one-column-layout>
      <nb-menu [items]="getMenu() | async"></nb-menu>
      <router-outlet></router-outlet>
    </ngx-one-column-layout>
  `,
})
export class PagesComponent implements OnInit, OnDestroy {
  private _menu: Subject<NbMenuItem[]>;
  menu$: Observable<NbMenuItem[]>;

  loggedInSubscription: Subscription;

  constructor(private authService: AuthService) {
    this._menu = new BehaviorSubject<NbMenuItem[]>([]);

    this.loggedInSubscription = this.authService
      .loggedIn()
      .subscribe(() => this.updateMenu());
  }

  ngOnDestroy(): void {
    if (this.loggedInSubscription) {
      this.loggedInSubscription.unsubscribe();
    }
  }

  getMenu(): Observable<NbMenuItem[]> {
    return this._menu.asObservable();
  }

  ngOnInit() {
    this.updateMenu();
  }

  updateMenu() {
    this._menu.next([
      {
        title: "Home",
        icon: "home-outline",
        link: "/pages/dashboard",
        home: true,
      },
      {
        title: "Admin",
        link: "/pages/admin",
        icon: "unlock-outline",
        hidden:
          this.authService.getCurrentUser()?.role != "Admin" ? true : false,
      },
      {
        title: "Log In",
        icon: "log-in-outline",
        link: "/pages/login",
        hidden: this.authService.getLoggedIn(),
      },
      {
        title: "Create",
        icon: "plus-square-outline",
        link: "/pages/create",
      },
      {
        title: "Like Button",
        icon: "heart-outline",
        link: "/pages/likebutton",
      },
      {
        title: "Profile",
        icon: "person-outline",
        link: "/pages/profile",
      },
    ]);
  }
}
