import { HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MemeService } from '../_helpers/MemeService';

@Injectable()
export class Interceptor implements HttpInterceptor
{
    constructor ( private memeService: MemeService )
    {
        console.log('interceptor instantiated, memeService = ', memeService != undefined);
        if (!memeService)
        {
            console.error('meService should be defined');
        }
    }

    intercept ( req: HttpRequest<any>, next: HttpHandler )
    {
        return next.handle(req);
    }
}
