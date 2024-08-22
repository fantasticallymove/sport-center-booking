/**
 * 『運動中心-半自動預約系統』
 * 操作步驟:
 * 1.於運動中心登入後將你右上角的登入名稱複製下來去網站把該中文轉unicode編碼填入 application.properties 中的 sport.center.username
 * 例如: 謝○富 -> \u8b1d\u25cb\u5bcc
 * 2.運動中心登入 在瀏覽器按下F12 進到application看一下 該域名cookie 的 ASP.NET_SessionId 值, 複製下來後進到SportCenterSettingEnum
 * 3.在對應的運動中心aspToken填入
 * 4.找到BookingSchedule類在排程executeFetchQidInfo 針對需要抓取時段場地代碼的時段做設定
 * PS: 需要進行自動預約的中心 複製下來後進到SportCenterSettingEnum下的自動預約 autoBookingEnabled 請設定成true
