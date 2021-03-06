package br.edu.ifsp.spo.bulls.common.api.enums;

public enum CodeException {

    AU001("AU001: Author not found", "AU001"),
    AU002("AU002: Author already exists", "AU002"),
    AT001("AT001: Error, wrong password or user not found", "AT001"),
    AT002("AT002: Error, token not found", "AT002"),
    AT003("AT003: Password already changed", "AT003"),
    US001("US001: User not found", "US001"),
    US002("US002: Email already used", "US002"),
    US003("US003: Password can't be null", "US003"),
    US004("US004: Token not found", "US004"),
    US005("US005: Username already used", "US005"),
    BK001("BK001: ISBN already exists", "BK001"),
    BK002("BK002: Book not found", "BK002"),
    BK003("BK003: Book should have at least 1 author", "BK003"),
    RT001("RT001: Reading Tracking not found", "RT001"),
    RT002("RT002: Page number greater than the total pages of the book", "RT002"),
    RT003("RT003: Book already completed", "RT002"),
    RT004("RT004: Conflict IDs", "RTO04"),
    RT005("RT005: Page smaller than last tracking", "RT005"),
    RT006("RT006: Page can´t be zero", "RT006"),
    TA001("TA001: Last tracking is not finished yeat", "TA001"),
    TA002("TA002: TrackingGroup not found", "TA002"),
    TG001("TG001: Tag not found", "TG001"),
    TG002("TG002: Tag sent does not match ID", "TG002"),
    BAD001("BAD001: Book ad not found", "BAD001"),
    BAD002("BAD002: User is not owner", "BAD002"),
    BAD003("BAD003: Cannot delete when has exchanges", "BAD003"),
    EXC001("EXC001: RequesterId is not equals to requesterAds or ReceiverAds", "EXC001"),
    EXC002("EXC002: Exchange not found", "EXC002"),
    EXC003("EXC003: This ad is not from this user", "EXC003"),
    EXC004("EXC003: This users is not who created", "EXC004"),
    PF001("PF001: Profile not found", "PF001"),
    UB001("UB001: UserBook not found", "UB001"),
    UB002("UB002: Invalid status", "UB002"),
    UB003("UB003: Userbook already in bookcase", "UB003"),
    UB004("UB004: Userbook sent does not match ID", "UB004"),
    TK001("TK001: Invalid token", "TK001"),
    FR001("FR001: Request not found", "FR001"),
    FR002("FR002: Conflict while removing friend", "FR002"),
    BR001("BR001: Book recommendation not found", "BR001"),
    RE001("RE001: Review not found", "RE001"),
    RE002("RE002: Review sent does not match ID", "RE002"),
    RTG001("RTG001: Reading target not found", "RTG001"),
    RTG002("RTG002: Reading target already exists", "RTG002"),
    RTG003("RTG003: Userbook not in the list", "RTG003"),
    PT001("PT001: Post not found", "PT001"),
    PT002("PT001: This post is not yours", "PT002"),
    GR001("GR001: Group not found", "GR001"),
    GR002("GR002: Group name already exists", "GR002"),
    GR003("GR003: Requester is not group's owner", "GR003"),
    GR004("GR004: Requester is not group's owner or admin", "GR004"),
    CP001("CP001: Competition not found", "CP001"),
    CM001("CM001: Competition member not found", "CM001"),
    CM002("CM002: Profile not authorized, id not match", "CM002"),
    CM003("CM003: Profile requester not a member of competition", "CM003"),
    CM004("CM004: Profile already in competition", "CM004"),
    CM005("CM005: Only owner can create/delete admin members", "CM005"),
    CM006("CM006: You cannot add or edit other profile in competition", "CM006"),
    CV001("CV001: You are not authorized to vote as other profile", "CV001"),
    CV002("CV002: The competition member was not accepted at the competition", "CV002"),
    CV003("CV003: You cant vote more than 1 one time in the same competitor", "CV003"),
    CV004("CV004: Vote not found", "CV004"),
    GR005("GR005: Requester is has not access to this data", "GR005"),
    GR006("GR006: invite already sent", "GR006"),
    INV001("INV001: Invite not found", "INV001"),
    INV002("INV002: You have no permission to do this", "INV002"),
    UPF001("UPF001: Public profile not found", "UPF001"),
    UPF002("UPF002: Public profile already exists", "UPF002"),
    UPF003("UPF003: not owner of this public profile", "UPF003");


    private final String text;
    private final String code;

    CodeException(final String text, String number) {
        this.text = text;
        this.code = number;
    }

    public String getText() {
        return text;
    }

    public String getCode() {
        return code;
    }
}
