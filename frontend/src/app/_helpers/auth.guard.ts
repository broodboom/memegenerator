import { Injectable } from "@angular/core";
import {
  Router,
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from "@angular/router";
import { AuthService } from "app/services/auth.service";

@Injectable({ providedIn: "root" })
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private authService: AuthService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.authService.getCurrentUser();
    const loggedIn = this.authService.getLoggedIn();

    debugger;

    if (loggedIn && currentUser) {
      // check if route is restricted by role
      if (
        route.data.roles &&
        route.data.roles.indexOf(currentUser.role) === -1
      ) {
        // role not authorised so redirect to home page
        this.router.navigate(["/pages/dashboard"]);
        return false;
      }

      // authorised so return true
      return true;
    }

    // not logged in so redirect to dashboard page with the return url
    this.router.navigate(["/pages/dashboard"], {
      queryParams: { returnUrl: state.url },
    });

    return false;
  }
}
