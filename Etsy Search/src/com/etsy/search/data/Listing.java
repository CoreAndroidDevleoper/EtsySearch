
package com.etsy.search.data;

import java.util.Arrays;

/***
 * This class is the wrapper class for the Listing
 */
public class Listing {

    private String mListingId;
    private String mState;
    private String mUserId;
    private String mCategoryId;
    private String mTitle;
    private String mDescription;
    private String mCreationTsz;
    private String mEndingTsz;
    private String mOriginalCreationTsz;
    private String mLastModifiedTsz;
    private String mPrice;
    private String mCurrencyCode;
    private String mQuantity;
    private String[] mTags; // Set of items
    private String[] mCategoryPath; // Set of items
    private String[] mCategoryPathIds; // Set of items
    private String[] mMaterials; // Set of items
    private String mShopSectionId;
    private String mFeaturedRank;
    private String mStateTsz;
    private String mUrl;
    private String mViews;
    private String mNumFavorers;
    private String mShippingTemplateId;
    private String mProcessingMin;
    private String mProcessingMax;
    private String mWhoMade;
    private String mIsSupply;
    private String mWhenMade;
    private String mIsPrivate;
    private String mRecipient;
    private String mOccasion;
    private String mStyle;
    private String mNonTaxable;
    private String mIsCustomizable;
    private String mIsDigital;
    private String mFileData;
    private String mIsta;
    private String mLanguage;
    private String mHasVariations;
    private String mUsedManufacturer;
    private ListingImage mListingImage;

    public ListingImage getListingImage() {
        return mListingImage;
    }

    public void setListingImage(ListingImage listingImage) {
        this.mListingImage = listingImage;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        this.mPrice = price;
    }

    public String[] getTags() {
        return mTags;
    }

    public void setTags(String[] tags) {
        this.mTags = tags;
    }

    public String getShopSectionId() {
        return mShopSectionId;
    }

    public void setShopSectionId(String shopSectionId) {
        this.mShopSectionId = shopSectionId;
    }

    public String getFeaturedRank() {
        return mFeaturedRank;
    }

    public void setFeaturedRank(String featured_rank) {
        this.mFeaturedRank = featured_rank;
    }

    public String getStateTsz() {
        return mStateTsz;
    }

    public void setStateTsz(String state_tsz) {
        this.mStateTsz = state_tsz;
    }

    public String getIsSupply() {
        return mIsSupply;
    }

    public void setIsSupply(String is_supply) {
        this.mIsSupply = is_supply;
    }

    public String getWhenMade() {
        return mWhenMade;
    }

    public void setWhenMade(String when_made) {
        this.mWhenMade = when_made;
    }

    public String getIsPrivate() {
        return mIsPrivate;
    }

    public void setIsPrivate(String is_private) {
        this.mIsPrivate = is_private;
    }

    public String getRecipient() {
        return mRecipient;
    }

    public void setRecipient(String recipient) {
        this.mRecipient = recipient;
    }

    public String getOccasion() {
        return mOccasion;
    }

    public void setOccasion(String occasion) {
        this.mOccasion = occasion;
    }

    public String getStyle() {
        return mStyle;
    }

    public void setStyle(String style) {
        this.mStyle = style;
    }

    public String getNonTaxable() {
        return mNonTaxable;
    }

    public void setNonTaxable(String non_taxable) {
        this.mNonTaxable = non_taxable;
    }

    public String getIsCustomizable() {
        return mIsCustomizable;
    }

    public void setIsCustomizable(String is_customizable) {
        this.mIsCustomizable = is_customizable;
    }

    public String getIsDigital() {
        return mIsDigital;
    }

    public void setIsDigital(String is_digital) {
        this.mIsDigital = is_digital;
    }

    public String getFileData() {
        return mFileData;
    }

    public void setFileData(String file_data) {
        this.mFileData = file_data;
    }

    public String getListingId() {
        return mListingId;
    }

    public void setListingId(String listing_id) {
        this.mListingId = listing_id;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String user_id) {
        this.mUserId = user_id;
    }

    public String getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(String category_id) {
        this.mCategoryId = category_id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getCreationTsz() {
        return mCreationTsz;
    }

    public void setCreationTsz(String creation_tsz) {
        this.mCreationTsz = creation_tsz;
    }

    public String getEndingTsz() {
        return mEndingTsz;
    }

    public void setEndingTsz(String ending_tsz) {
        this.mEndingTsz = ending_tsz;
    }

    public String getOriginalCreationTsz() {
        return mOriginalCreationTsz;
    }

    public void setOriginalCreationTsz(String original_creation_tsz) {
        this.mOriginalCreationTsz = original_creation_tsz;
    }

    public String getLastModifiedTsz() {
        return mLastModifiedTsz;
    }

    public void setLastModifiedTsz(String last_modified_tsz) {
        this.mLastModifiedTsz = last_modified_tsz;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public void setCurrencyCode(String currency_code) {
        this.mCurrencyCode = currency_code;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        this.mQuantity = quantity;
    }

    public String[] getCategoryPath() {
        return mCategoryPath;
    }

    public void setCategoryPath(String[] category_path) {
        this.mCategoryPath = category_path;
    }

    public String[] getCategoryPathIds() {
        return mCategoryPathIds;
    }

    public void setCategoryPathIds(String[] category_path_ids) {
        this.mCategoryPathIds = category_path_ids;
    }

    public String[] getMaterials() {
        return mMaterials;
    }

    public void setMaterials(String[] materials) {
        this.mMaterials = materials;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getViews() {
        return mViews;
    }

    public void setViews(String views) {
        this.mViews = views;
    }

    public String getNumFavorers() {
        return mNumFavorers;
    }

    public void setNumFavorers(String num_favorers) {
        this.mNumFavorers = num_favorers;
    }

    public String getShippingTemplateId() {
        return mShippingTemplateId;
    }

    public void setShippingTemplateId(String shipping_template_id) {
        this.mShippingTemplateId = shipping_template_id;
    }

    public String getProcessingMin() {
        return mProcessingMin;
    }

    public void setProcessingMin(String processing_min) {
        this.mProcessingMin = processing_min;
    }

    public String getProcessingMax() {
        return mProcessingMax;
    }

    public void setProcessingMax(String processing_max) {
        this.mProcessingMax = processing_max;
    }

    public String getWhoMade() {
        return mWhoMade;
    }

    public void setWhoMade(String who_made) {
        this.mWhoMade = who_made;
    }

    public String getIsta() {
        return mIsta;
    }

    public void setIsta(String ista) {
        this.mIsta = ista;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        this.mLanguage = language;
    }

    public String getHasVariations() {
        return mHasVariations;
    }

    public void setHasVariations(String has_variations) {
        this.mHasVariations = has_variations;
    }

    public String getUsedManufacturer() {
        return mUsedManufacturer;
    }

    public void setUsedManufacturer(String used_manufacturer) {
        this.mUsedManufacturer = used_manufacturer;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        this.mState = state;
    }

    @Override
    public String toString() {
        return "Listing [listing_id=" + mListingId + ", state=" + mState + ", user_id=" + mUserId
                + ", category_id=" + mCategoryId + ", title=" + mTitle + ", description="
                + mDescription + ", creation_tsz=" + mCreationTsz + ", ending_tsz=" + mEndingTsz
                + ", original_creation_tsz=" + mOriginalCreationTsz + ", last_modified_tsz="
                + mLastModifiedTsz + ", price=" + mPrice + ", currency_code=" + mCurrencyCode
                + ", quantity=" + mQuantity + ", tags=" + Arrays.toString(mTags)
                + ", category_path="
                + Arrays.toString(mCategoryPath) + ", category_path_ids="
                + Arrays.toString(mCategoryPathIds) + ", materials=" + Arrays.toString(mMaterials)
                + ", shop_section_id=" + mShopSectionId + ", featured_rank=" + mFeaturedRank
                + ", state_tsz=" + mStateTsz + ", url=" + mUrl + ", views=" + mViews
                + ", num_favorers=" + mNumFavorers + ", shipping_template_id="
                + mShippingTemplateId + ", processing_min=" + mProcessingMin + ", processing_max="
                + mProcessingMax + ", who_made=" + mWhoMade + ", is_supply=" + mIsSupply
                + ", when_made=" + mWhenMade + ", is_private=" + mIsPrivate + ", recipient="
                + mRecipient + ", occasion=" + mOccasion + ", style=" + mStyle + ", non_taxable="
                + mNonTaxable + ", is_customizable=" + mIsCustomizable + ", is_digital="
                + mIsDigital + ", file_data=" + mFileData + ", ista=" + mIsta + ", language="
                + mLanguage + ", has_variations=" + mHasVariations + ", used_manufacturer="
                + mUsedManufacturer + ", listingImage=" + mListingImage + "]";
    }

}
